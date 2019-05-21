/*
 * Copyright CISE AIS Adaptor (c) 2018, European Union
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package eu.cise.emulator.app;

//import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.cise.emulator.app.candidate.Sender;
import eu.cise.emulator.app.candidate.SourceStreamProcessor;
import eu.cise.emulator.app.context.ServerAppContext;
import eu.cise.emulator.app.context.SimConfig;
import eu.cise.emulator.app.resource.SourceBufferFileSource;
import eu.cise.emulator.app.resource.SourceBufferInterface;
import eu.cise.emulator.app.util.CrossOrigin;
import eu.cise.emulator.app.util.SimLogger;
import eu.cise.emulator.httptransport.*;
import eu.cise.emulator.httptransport.Validation.MessageValidator;
import eu.cise.emulator.httptransport.conformance.AcceptanceAgent;
import eu.cise.emulator.httptransport.conformance.DefaultAcceptanceAgent;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.signature.SignatureService;
import eu.cise.signature.SignatureServiceBuilder;
import eu.eucise.xml.CISENamespaceContext;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpression;
import java.util.List;

/**
 * Main class in the domain module. The run() method start the process.
 * <p>
 * Three phases are composing the transformation:
 * - toFluxString: generate the source and feed the flux stream of ais messages in
 * string format
 * - toCiseMessages: transform the string in {NMEAMessage}, then in {AISMessage}
 * and finally in {AisMsg}
 * - dispatchMessages: using multi threading dispatches the cise messages
 * to the conformance absorbing peaks
 */
public class SimApp<T extends Configuration> extends Application<T> implements Runnable {

    private final SimLogger logger;
    private final SimConfig config;
    private final SourceStreamProcessor streamProcessor;
    private final Sender sender;

    private final XmlMapper xmlMapper;
    private final MessageValidator validator;

    public boolean isDebug = false;
    public boolean isVerbose = false;
    public String payloadFile = null;
    public String serviceFile = null;
    //private JAXWSBundle jaxWsBundle = new JAXWSBundle();

    /**
     * The App is mainly built with a stream generator a processor and a message
     * dispatcher.
     * <p>
     * The generator will produce a stream of strings reading them from
     * different possible sources:
     * - plain text files
     * - tcp sockets
     * - whatever other AIS information producer
     * <p>
     * The processor will transform the incoming stream of ais strings into a
     * sequence of CISE push messages objects. The transformation will be
     * performed in multiple stages.
     * - String -&gt; AisMsg: where the latter is a decoded representation
     * of the message in an domain object
     * - AisMsg -&gt; {@code Optional<Entity>}: the ais message is translated
     * into a cise
     * vessel if is of type 1,2,3 or 5, otherwise it will be an empty optional.
     * - {@code List<Entity>} -&gt; Push:
     *
     * @param aStreamProcessor stream generator of ais strings
     * @param aSender          stream processor of ais strings into cise messages
     * @param logger           dispatcher of sim messages
     * @param config           application configuration object
     * @param xmlMapper
     * @param validator
     */
    public SimApp(SourceStreamProcessor aStreamProcessor,
                  Sender aSender,
                  SimLogger logger,
                  SimConfig config, XmlMapper xmlMapper, MessageValidator validator) {
        this.streamProcessor = aStreamProcessor;
        this.sender = aSender;
        this.logger = logger;
        this.config = config;
        this.xmlMapper = xmlMapper;
        this.validator = validator;
    }

    /*reader*/ private static final String[] CISE_DATA_MODEL_ELEMENT = new String[]{"Action", "Agent", "Aircraft", "Anomaly", "Cargo", "CargoDocument", "Catch", "CertificateDocument", "ContainmentUnit", "CrisisIncident", "Document", "EventDocument", "FormalOrganization", "Incident", "IrregularMigrationIncident", "LandVehicle", "LawInfringementIncident", "Location", "LocationDocument", "MaritimeSafetyIncident", "MeteoOceanographicCondition", "Movement", "NamedLocation", "OperationalAsset", "Organization", "OrganizationalCollaboration", "OrganizationalUnit", "OrganizationDocument", "Person", "PersonDocument", "PollutionIncident", "PortFacilityLocation", "PortLocation", "PortOrganization", "Risk", "RiskDocument", "Stream", "Vessel", "VesselDocument"};
    /*reader*/ private final NamespaceContext ciseNamespaceContext = new CISENamespaceContext();
    /*reader*/ private XPathExpression dataElementXPath;

    private String buildDataElementXPathPattern() {
        StringBuilder bld = new StringBuilder();
        bld.append("/*[local-name()='Push' or  local-name()='PullResponse'  or  local-name()='PullRequest' or local-name()='Feedback']/Payload/*");
        bld.append("[local-name() = '");
        bld.append(CISE_DATA_MODEL_ELEMENT[0]);
        bld.append("'");

        for (int i = 1; i < CISE_DATA_MODEL_ELEMENT.length; ++i) {
            bld.append(" or  local-name() = '");
            bld.append(CISE_DATA_MODEL_ELEMENT[i]);
            bld.append("'");
        }

        bld.append("]");
        return bld.toString();
    }

    private Message loadMessage(String servicefile, String payloadfile) {

        SourceBufferInterface sourceBuffer = new SourceBufferFileSource();
        StringBuffer serviceBuffer = sourceBuffer.serviceBufferParameter(servicefile);
        String completedMessage = "";

        XmlMapper mapper = new DefaultXmlMapper.Pretty();
        Message messageObject = mapper.fromXML(serviceBuffer.toString());
        if (!("".equals(payloadfile))) {
            StringBuffer payloadBuffer = sourceBuffer.payloadBufferParameter(payloadfile);
            if (!(payloadBuffer.toString().equals(""))) {
                //XmlEntityPayload sourcePayload = new XmlEntityPayload();
                Message resultMapper = mapper.fromXML(payloadBuffer.toString());
                //XmlEntityPayload res= (XmlEntityPayload)resultMapper;

                List<Object> res = ((XmlEntityPayload) resultMapper.getPayload()).getAnies();
                ((XmlEntityPayload) messageObject.getPayload()).getAnies().add(res);
            }
        }
        completedMessage = mapper.toXML(messageObject).toString();
        return messageObject;
    }

    public void sendEvent(String servicefile, String payloadfile) {
        Message message = loadMessage(servicefile, payloadfile);
        RestClient client = new JerseyRestClient();
        XmlMapper refmapper = new DefaultXmlMapper();
        String refMessage = refmapper.<Message>toXML(message);
        if (config.isSignatureMessageSend().contains("true")) {
            System.out.println("before signed  : " + refMessage);
            //XmlMapper refmapper2 = refmapper.fromXML(refMessage);
            SignatureServiceBuilder signBuilder = SignatureServiceBuilder.newSignatureService(refmapper);
            SignatureService signature = signBuilder
                    .withKeyStoreName((String) config.getKeyStoreName())
                    .withKeyStorePassword((String) config.getKeyStorePassword())
                    .withPrivateKeyAlias((String) config.getPrivateKeyAlias())
                    .withPrivateKeyPassword((String) config.getPrivateKeyPassword())
                    .build();
            message = signature.sign(message);
        }
        refMessage = refmapper.toXML(message);
        System.out.println("Server returned : " + refMessage.toString());

        RestResult result = client.post(config.getCounterpartRestUrl(), refMessage);
        System.out.println("Client send to Server : " + refMessage);
        System.out.println("Server returned : " + result.getBody());


        //System.exit(0);
    }

    @Override
    public void run(T t, Environment environment) throws Exception {
        CrossOrigin.setup(environment);

        ResponseAdapter responseAdapter = new ResponseAdapter();
        ServerAppContext factory = new ServerAppContext();

        DefaultAcceptanceAgent messageProcessor = factory.makeMessageProcessor();

        environment.jersey().register(new ServerRestConcrete(config.getSimulatorId(), (AcceptanceAgent) messageProcessor));
        //jaxWsBundle.publishEndpoint(new EndpointBuilder("/hello", new ServerSoapConcrete()));

    }

    ;


    @Override
    public void run() {
    }

    @Override
    public String getName() {
        return "Gateway";
    }


    @Override
    public void initialize(Bootstrap<T> bootstrap) {
        System.out.println("simapp initialised : " + bootstrap.getApplication().getName());
        //bootstrap.addBundle(jaxWsBundle);
    }


}

//REPENTIR
// note READER mean excerp from the class DefaultXmlValidator
