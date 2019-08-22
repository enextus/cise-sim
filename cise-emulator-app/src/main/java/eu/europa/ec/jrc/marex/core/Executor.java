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

package eu.europa.ec.jrc.marex.core;


import eu.cise.datamodel.v1.entity.vessel.NavigationalStatusType;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.datamodel.v1.entity.vessel.VesselType;
import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.eucise.helpers.AckBuilder;
import eu.eucise.helpers.DateHelper;
import eu.eucise.xml.DefaultXmlValidator;
import eu.eucise.xml.XmlValidator;
import eu.europa.ec.jrc.marex.CiseEmulatorApplication;
import eu.europa.ec.jrc.marex.candidate.RestClient;
import eu.europa.ec.jrc.marex.client.RestResult;
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration;
import eu.europa.ec.jrc.marex.candidate.SourceBufferFileSource;
import eu.europa.ec.jrc.marex.candidate.SourceBufferInterface;
import eu.europa.ec.jrc.marex.core.sub.*;
import eu.europa.ec.jrc.marex.util.SimLogger;


import eu.cise.signature.SignatureService;
import eu.cise.signature.SignatureServiceBuilder;

import eu.eucise.xml.CISENamespaceContext;
import eu.eucise.xml.XmlMapper;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpression;
import java.util.Date;
import java.util.UUID;

import static eu.cise.servicemodel.v1.message.InformationSecurityLevelType.NON_CLASSIFIED;
import static eu.cise.servicemodel.v1.message.InformationSensitivityType.GREEN;
import static eu.cise.servicemodel.v1.message.PriorityType.LOW;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.ServiceBuilder.newService;

/**
 * Main class in the domain module. The config() method consolidate the class to support domain main process.
 */
public class Executor {

    private final SimLogger logger;
    private final CiseEmulatorConfiguration config;
    //private final SourceStreamProcessor streamProcessor;
    private final Sender sender;

    private final XmlValidator xmlValidator = new DefaultXmlValidator();

    private XmlMapper xmlMapper = null;
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
    public Executor(SourceStreamProcessor aStreamProcessor,
                    Sender aSender,
                    SimLogger logger,
                    CiseEmulatorConfiguration config, XmlMapper xmlMapper, MessageValidator validator) {
        // this.streamProcessor = aStreamProcessor;
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

    public Message LoadMessage(String servicefile, String payloadfile) {
        String pathDefault= System.getProperty("user.dir");
        SourceBufferInterface sourceBuffer = new SourceBufferFileSource();
        StringBuffer templateMessageBuffer = sourceBuffer.getReferenceFileContent(servicefile.startsWith("./") ? pathDefault + servicefile.substring(1) :servicefile );
        Message TemplateMessage = loadContent(templateMessageBuffer);
        StringBuffer payloadMessageBuffer = sourceBuffer.getReferenceFileContent(payloadfile.startsWith("./") ? pathDefault + payloadfile.substring(1) :payloadfile);
        if (!(payloadMessageBuffer.toString().isEmpty())) {
            Message payloadMessage = loadContent(payloadMessageBuffer);
            TemplateMessage.setPayload(payloadMessage.getPayload());
        }
        return TemplateMessage;
    }

    private Message loadContent(StringBuffer serviceBuffer) {
        // detect if XmlEntity / Message
        Message messageObject = null;
        try {
            messageObject = xmlMapper.fromXML(serviceBuffer.toString());
        } catch (Exception e) {
            messageObject = createEmptyMessage();
            XmlEntityPayload payload = new XmlEntityPayload();
            // copy the key information
            payload.setInformationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED);
            payload.setInformationSensitivity(InformationSensitivityType.AMBER);
            payload.setIsPersonalData(false);
            payload.setPurpose(PurposeType.BORDER_MONITORING);
            payload.setRetentionPeriod(DateHelper.toXMLGregorianCalendar(new Date()));
            payload.setEnsureEncryption(false);
            // read entity > include them for instance
            Vessel v = new Vessel();
            v.getNames().add("The Mother Queen");
            v.setDeadweight(30);
            v.setDraught(30.4);
            v.setGrossTonnage(34.5);
            v.setMMSI(45545454L);
            v.setIMONumber(435454L);
            v.setLength(54.56);
            v.setNavigationalStatus(NavigationalStatusType.ENGAGED_IN_FISHING);
            v.getShipTypes().add(VesselType.FISHING_VESSEL);
            v.setYearBuilt(1978);
            payload.getAnies().add(v);

            // end with charging the payload
            messageObject.setPayload(payload);
        }
        return messageObject;
    }

    public RestResult sendEvent(Message loadMessage, boolean sign) {
        RestClient client = new JerseyRestClient();
        if (config.getSignatureOnSend().contains("true")) {
            SignatureServiceBuilder signBuilder = SignatureServiceBuilder.newSignatureService(xmlMapper);
            SignatureService signature = signBuilder
                    .withKeyStoreName((String) config.getKeyStoreFileName())
                    .withKeyStorePassword((String) config.getKeyStorePassword())
                    .withPrivateKeyAlias((String) config.getCertificateKey())
                    .withPrivateKeyPassword((String) config.getCertificatePassword())
                    .build();
            loadMessage = signature.sign(loadMessage);
        }
        RestResult result = client.post(config.getCounterpartUrl(), xmlMapper.toXML(loadMessage));
        return result;
    }

    public ValidationResult validateIncoming(String inputXmlMessage) {
        ValidationResult result = new ValidationResult();
        try {
            xmlValidator.validate(inputXmlMessage);
            result.setOkXML(true);
        } catch (Exception e) {
            result.setOkXML(false);
        }
        Message inputMessage = null;
        try {
            inputMessage = xmlMapper.fromXML(inputXmlMessage);
            result.setOkEntity(true);
        } catch (Exception e) {
            result.setOkEntity(false);
        }
        try {
            SignatureServiceBuilder signBuilder = SignatureServiceBuilder.newSignatureService(xmlMapper);
            SignatureService signature = signBuilder
                    .withKeyStoreName((String) config.getKeyStoreFileName())
                    .withKeyStorePassword((String) config.getKeyStorePassword())
                    .withPrivateKeyAlias((String) config.getCounterpartCertificate())
                    .build();
            signature.verify(inputMessage);
            result.setOkSignedEntity(true);
        } catch (Exception e) {
            result.setOkSignedEntity(false);
        }
        result.setOkSemantic(true);
        return result;
    }


    public Message createEmptyMessage() {
        Push msg = new Push();
        String uuid = UUID.randomUUID().toString();
        msg.setContextID(uuid);
        msg.setCorrelationID(uuid);
        msg.setCreationDateTime(DateHelper.toXMLGregorianCalendar(new Date()));
        msg.setMessageID(uuid);
        msg.setPriority(PriorityType.HIGH);
        msg.setRequiresAck(false);
        Service sender = new Service();
        sender.setServiceID("es.gc-ls01.maritimesafetyincident.pullresponse.gcs04");
        sender.setServiceOperation(ServiceOperationType.PULL);
        msg.setSender(sender);
        Service receiver = new Service();
        receiver.setServiceID("myService2");
        receiver.setServiceOperation(ServiceOperationType.PULL);
        msg.setRecipient(receiver);
        return msg;
    }


    public String AcknowledgmentSuccessMessage(String inputXmlMessage) {
        Message inputMessage = xmlMapper.fromXML(inputXmlMessage);
        String literalMessageAckReturn = xmlMapper.toXML(buildAck(inputMessage, AcknowledgementType.SUCCESS).build());
        return literalMessageAckReturn;
    }

    public String AcknowledgmentFailMessage(String inputXmlMessage, String ErrorType) {
            Message inputMessage = xmlMapper.fromXML(inputXmlMessage);
            String literalMessageAckReturn = "";
            switch (ErrorType){
                case ("BAD_REQUEST"):
                    literalMessageAckReturn = xmlMapper.toXML(buildAck(inputMessage, AcknowledgementType.BAD_REQUEST).build());
                case ("SECURITY_ERROR"):
                    literalMessageAckReturn = xmlMapper.toXML(buildAck(inputMessage, AcknowledgementType.SECURITY_ERROR).build());
            }
            return literalMessageAckReturn;
        }

    private AckBuilder buildAck (Message inputMessage, AcknowledgementType atype) {
        String uuid = UUID.randomUUID().toString();

        AckBuilder ackBuild= newAck()
                .id(uuid) ;
        Participant recipientParticipant= inputMessage.getRecipient().getParticipant();
        if (recipientParticipant != null) {
            String recipient_participantId=recipientParticipant.getId();
            String recipient_endpointUrl=recipientParticipant.getEndpointUrl();
            ackBuild.recipient(newService()
                        .id(inputMessage.getRecipient().getServiceID())
                        .operation(ServiceOperationType.ACKNOWLEDGEMENT)
                        .participantId(recipient_participantId)
                        .participantUrl(recipient_endpointUrl));
    }else{
        ackBuild.recipient(newService()
                .id(inputMessage.getRecipient().getServiceID())
                .operation(ServiceOperationType.ACKNOWLEDGEMENT));
    }
        Participant senderParticipant= inputMessage.getRecipient().getParticipant();
        if (senderParticipant != null) {
            String sender_participantId = recipientParticipant.getId();
            String sender_endpointUrl = recipientParticipant.getEndpointUrl();
            ackBuild.sender(newService()
                    .id(inputMessage.getSender().getServiceID())
                    .operation(ServiceOperationType.ACKNOWLEDGEMENT)
                    .participantId(sender_participantId)
                    .participantUrl(sender_endpointUrl));
        }else{
            ackBuild.sender(newService()
                    .id(inputMessage.getSender().getServiceID())
                    .operation(ServiceOperationType.ACKNOWLEDGEMENT));
        }

        ackBuild.correlationId(inputMessage.getCorrelationID())
                .creationDateTime(new Date())
                .informationSecurityLevel(inputMessage.getPayload().getInformationSecurityLevel())
                .informationSensitivity(inputMessage.getPayload().getInformationSensitivity())
                .purpose(inputMessage.getPayload().getPurpose())
                .priority(inputMessage.getPriority())
                .ackCode(atype);
        if (inputMessage.isRequiresAck() != null)
            ackBuild.isRequiresAck(inputMessage.isRequiresAck());

        return ackBuild;
    }

}

