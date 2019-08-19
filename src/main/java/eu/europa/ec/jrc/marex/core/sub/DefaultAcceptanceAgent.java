package eu.europa.ec.jrc.marex.core.sub;



import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.eucise.helpers.AckBuilder;
import eu.eucise.xml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

import static eu.cise.servicemodel.v1.message.AcknowledgementType.*;
import static eu.cise.servicemodel.v1.message.InformationSecurityLevelType.NON_CLASSIFIED;
import static eu.cise.servicemodel.v1.message.InformationSensitivityType.GREEN;
import static eu.cise.servicemodel.v1.message.PriorityType.HIGH;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static java.lang.String.format;

public class DefaultAcceptanceAgent implements treatIncomingAgent {

    private final Logger gatewayLogger  = LoggerFactory.getLogger("eu.cise.sim.transport.DefaultAcceptanceAgent");;
    private final XmlMapper xmlMapper;
    private final XmlValidator xmlValidator;
    private String parameter;

    public DefaultAcceptanceAgent(XmlMapper xmlMapper,
                                  XmlValidator xmlValidator) {

        this.xmlMapper = xmlMapper;
        this.xmlValidator = xmlValidator;
    }

    @Override
    public AcceptanceResponse accept(String messageXML) {


        try {
            /*xmlValidator.validate(messageXML);*/

           /* submissionAgent.forward(xmlMapper.fromXML(messageXML));*/

            return AcceptanceResponse.PROCESSED.withXmlBody(xmlMapper.toXML(processed()));

        } catch (XmlNotParsableException | CISEMalformedXmlException | CISEXmlValidationException xnpe) {
            return AcceptanceResponse.XML_MALFORMED.withXmlBody(xmlMapper.toXML(xmlMalformed(xnpe, messageXML)));
        } catch (IllegalMessageException ime) { // TODO We should use a specific exception for the validation
            return AcceptanceResponse.VALIDATION_ERROR.withXmlBody(xmlMapper.toXML(validationError(ime, messageXML)));
        } catch (InvalidMessageSignatureException imse) { // TODO We should use a specific exception for the validation
            return AcceptanceResponse.INVALID_SIGNATURE.withXmlBody(xmlMapper.toXML(signatureError(imse, messageXML)));
        } catch (Throwable t) {
            return AcceptanceResponse.INTERNAL_ERROR.withXmlBody(xmlMapper.toXML(internalError(t)));
        }

    }

    private AckBuilder buildAck() {
        String uuid = UUID.randomUUID().toString();
        String na = "N/A";
        return newAck()
                .id(uuid)
                .recipient(newService()
                        .id(na)
                        .operation(ServiceOperationType.ACKNOWLEDGEMENT)
                        .participantId(na)
                        .participantUrl(na))
                .sender(newService()
                        .id(na)
                        .operation(ServiceOperationType.ACKNOWLEDGEMENT)
                        .participantId(na)
                        .participantUrl(na))
                .correlationId(na)
                .creationDateTime(new Date())
                .informationSecurityLevel(NON_CLASSIFIED)
                .informationSensitivity(GREEN)
                .purpose(PurposeType.NON_SPECIFIED)
                .priority(HIGH)
                .isRequiresAck(false)
                ;

    }


    private Acknowledgement signatureError(InvalidMessageSignatureException e, String payload) {
        return buildAck().ackCode(BAD_REQUEST)
                .ackDetail(format("XML Invalid Signature. Message sender not verified. %s\n\n%s",
                        e.getMessage(), payload))
                .build();
    }

    private Acknowledgement processed() {
        return buildAck().ackCode(SUCCESS).build();
    }

    private Acknowledgement xmlMalformed(Exception e, String payload) {
        return buildAck().ackCode(BAD_REQUEST)
                .ackDetail(format("XML not parsable. %s\n\n%s",
                        e.getMessage(), payload))
                .build();
    }

    private Acknowledgement validationError(IllegalMessageException e, String payload) {
        return buildAck().ackCode(BAD_REQUEST)
                .ackDetail(format("XML not valid. %s\n\n%s",
                        e.getMessage(), payload))
                .build();
    }

    private Acknowledgement internalError(Throwable e) {
        e.printStackTrace();
        return buildAck().ackCode(SERVER_ERROR)
                .ackDetail(e.getMessage() + (e.getStackTrace().toString()))
                .build();
    }
    public  Acknowledgement treatIncomingMessage(Message receivedMessage)  {
        XmlMapper refmapper= new DefaultXmlMapper();
        String refMessage;
        refMessage = refmapper.<Message>toXML(receivedMessage);
        //extract attributes
        String correlationId= receivedMessage.getCorrelationID();
        String contextId= receivedMessage.getContextID();
        String messageId= receivedMessage.getMessageID();
        PriorityType priority= receivedMessage.getPriority();
        ReliabilityProfile reliability= receivedMessage.getReliability();
        Service recipient= receivedMessage.getRecipient();
        Service sender= receivedMessage.getSender();
        AcceptanceResponse returnResponse= accept(xmlMapper.toXML(receivedMessage));

        /*respond acknowledge*/
        Acknowledgement returnMessage = buildAck().build();
        returnMessage.setCorrelationID(correlationId);
        returnMessage.setMessageID(messageId);
        returnMessage.setContextID(contextId);
        returnMessage.setReliability(reliability);
        returnMessage.setPriority(priority);
        returnMessage.setRecipient(recipient);
        returnMessage.setSender(sender);
        returnMessage.setAckDetail("");
        /**/
        return returnMessage;
    }
}


/*REPENTIR : from package jrc.cise.gw.preprocessing
 class DefaultAcceptanceAgent implements AcceptanceAgent {
 */
