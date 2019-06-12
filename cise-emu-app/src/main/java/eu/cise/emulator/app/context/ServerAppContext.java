package eu.cise.emulator.app.context;

import eu.cise.emulator.app.candidate.Sender;
import eu.cise.emulator.app.util.SimLogger;
import eu.cise.emulator.httptransport.*;
import eu.cise.emulator.httptransport.Validation.MessageValidator;
import eu.cise.emulator.httptransport.conformance.*;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.cise.signature.SignatureServiceBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.DefaultXmlValidator;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlValidator;
import org.aeonbits.owner.ConfigFactory;

public class ServerAppContext {

    private final SimConfig config;

    private final XmlMapper xmlMapper;

    private final MessageValidator validator;
    private final SimLogger logger;
    private final SignatureService signatureService;/*see after note ... altered interface*/
    private final XmlValidator xmlValidator;
    private final AcceptanceAgent acceptanceAgent ;
    private final SubmissionAgent submissionAgent ;
    public ServerAppContext() {
        this.config = ConfigFactory.create(SimConfig.class);

        this.xmlMapper = new DefaultXmlMapper.NotValidating();
        this.xmlValidator = new DefaultXmlValidator();
        this.validator = new MessageValidator();
        this.logger = new SimLogger() {
            @Override
            public void logSenderResponses(Sender result) {

            }

            @Override
            public void logSenderError(Throwable throwable) {

            }
        };


        this.signatureService = SignatureServiceBuilder.newSignatureService()
                .withKeyStoreName(config.getKeyStoreName()).
                withKeyStorePassword(config.getKeyStorePassword())
                .withPrivateKeyAlias(config.getPrivateKeyAlias())
                .withPrivateKeyPassword(config.getPrivateKeyPassword())
                .build();

         GatewayProcessor submissionGatewayProcessor =  new GatewayProcessor() {
             @Override
             public void process(Message message) {
                 notify();
             }
         };//newInstanceOf(config.getSubmissionGatewayProcessor(), GatewayProcessor.class);


        //MessageValidator validator,GatewayProcessor gatewayProcessor, String gatewayAddressString)
          DefaultSubmissionAgent asubmitagent = new DefaultSubmissionAgent(new MessageValidator(), submissionGatewayProcessor,config.getSimulatorId()) ;
         this.submissionAgent = new SignatureDecorator( asubmitagent ,  signatureService);

        this. acceptanceAgent = (AcceptanceAgent) new DefaultAcceptanceAgent(xmlMapper,xmlValidator);




        if (config.getWebappWsMode().contains("REST")){
            Server myserver=  (ServerRestConcrete) new ServerRestConcrete(config.getSimulatorId(), acceptanceAgent);
            ((ServerRestConcrete)myserver).SetupServerRestConcrete(config.getSimulatorId(),acceptanceAgent );
            myserver=  (ServerRest) myserver;

        }else{
            Server myserver=  (ServerSoapConcrete) new ServerSoapConcrete();
            ((ServerSoapConcrete)myserver).SetupServerSoapConcrete(config.getSimulatorId(), acceptanceAgent);
            myserver=  (ServerSoap) myserver;

        }

    }

    public DefaultAcceptanceAgent makeMessageProcessor() {
        return new DefaultAcceptanceAgent( xmlMapper, xmlValidator);
    }


}
