package eu.cise.emulator.app.resource;

import ch.qos.logback.classic.Logger;
import eu.cise.emulator.app.context.SimConfig;
import eu.cise.emulator.app.core.InstanceID;
import eu.cise.emulator.app.core.WebSocketMessage;
import eu.cise.emulator.app.core.WebSocketMessageType;
import eu.cise.emulator.app.transport.OutBoundWebSocketClient;
import eu.cise.emulator.integration.Validation.MessageValidator;
import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceStatusType;
import eu.cise.signature.SignatureService;
import eu.cise.signature.SignatureServiceBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/sim-LSA/rest/")
public class InboundRESTMessageService {
    private final MessageValidator validator;
    private final Logger mylogger;
    private final XmlMapper mapper;
    private final SignatureService signature;
    private final OutBoundWebSocketClient outRestMessageclient;
    SimConfig config;
    InstanceID instanceID;

    public InboundRESTMessageService(InstanceID instanceID, SimConfig config, OutBoundWebSocketClient outRestMessageclient) {
        this.config = config;
        this.instanceID = instanceID;
        this.validator = new MessageValidator();
        this.mylogger = (Logger) LoggerFactory.getLogger(InboundRESTMessageService.class.getName());
        this.mapper = new DefaultXmlMapper.Pretty();
        SignatureServiceBuilder signBuilder = SignatureServiceBuilder.newSignatureService(mapper);
        this.outRestMessageclient = outRestMessageclient;
        this.signature = signBuilder
                .withKeyStoreName((String) config.getKeyStoreName())
                .withKeyStorePassword((String) config.getKeyStorePassword())
                .withPrivateKeyAlias((String) config.getPrivateKeyAlias())
                .withPrivateKeyPassword((String) config.getPrivateKeyPassword())
                .build();
    }


    @POST
    @Consumes("text/plain,text/xml,application/xml")
    @Produces("text/plain")
    @Path("/CISEMessageServiceREST")
    public String receiveMessage(String messageContent) throws Exception {


        Message message = null;
        Service defaultService = createServiceDummy();
        // initial loading phase
        String phase = "received XML initial loading ";
        // create  dummy service definition for standard initial phase error response.
        Acknowledgement thisAcknoledgment = new Acknowledgement();
        thisAcknoledgment.setSender(defaultService);
        thisAcknoledgment.setRecipient(defaultService);

        mylogger.debug("CISEMessageServiceRESTXML received POST message :// " + messageContent);
        phase = "XML validation";
        try {
            message = mapper.fromXML(messageContent);
        } catch (Exception e) {
            thisAcknoledgment.setAckCode(AcknowledgementType.BAD_REQUEST);
            mylogger.debug("CISEMessageServiceRESTXML reject message in " + phase);
            // add event to the websocket (queue)
            return mapper.toXML(thisAcknoledgment);
        }
        phase = "content validation";
        try {
            validator.validates(message, v -> {
                v.messageNotNullCheck();
                v.senderIdNotNullCheck();
                v.destinationAddressingCheck();
                v.senderAddressNotNullCheck();
            });
        } catch (Exception e) {
            thisAcknoledgment.setAckCode(AcknowledgementType.INVALID_REQUEST_OBJECT);
            mylogger.debug("CISEMessageServiceRESTXML reject message in " + phase);
            // add event to the websocket (queue)
            return mapper.toXML(thisAcknoledgment);
        }
        phase = "signature validation";
        thisAcknoledgment = conformAcknowledgment(message);
        thisAcknoledgment.setAckCode(AcknowledgementType.SUCCESS);
        /*insure connected*/
        outRestMessageclient.startConnect();
        /*create message*/
        WebSocketMessage aWSMessage = new WebSocketMessage();
        aWSMessage.setMember(message.getSender().getServiceID());
        aWSMessage.setAcknowledgment(mapper.toXML(thisAcknoledgment));
        aWSMessage.setData(messageContent);
        aWSMessage.setStatus(AcknowledgementType.SUCCESS.name());
        aWSMessage.setType(WebSocketMessageType.TEXT_MESSAGE);
        outRestMessageclient.sendMessage(aWSMessage);
        // add event to the websocket (queue)
        return mapper.toXML(thisAcknoledgment);
    }

    private Acknowledgement conformAcknowledgment(Message message) {
        Acknowledgement thisAcknoledgment = new Acknowledgement();
        // secure to use
        thisAcknoledgment.setContextID(message.getContextID());
        thisAcknoledgment.setCorrelationID(message.getCorrelationID());
        thisAcknoledgment.setPriority(message.getPriority());
        thisAcknoledgment.setReliability(message.getReliability());
        // we should clarify
        thisAcknoledgment.setMessageID(message.getMessageID());
        thisAcknoledgment.setRecipient(message.getRecipient());
        thisAcknoledgment.setSender(message.getSender());
        thisAcknoledgment.setCreationDateTime(message.getCreationDateTime());
        return thisAcknoledgment;
    }

    Service createServiceDummy() {
        Service defaultService = new Service();
        defaultService.setServiceID("N/A");
        defaultService.setServiceStatus(ServiceStatusType.MAINTENANCE);
        defaultService.setSeaBasin(SeaBasinType.OUTERMOST_REGIONS);
        defaultService.setServiceRole(ServiceRoleType.CONSUMER);
        defaultService.setServiceOperation(ServiceOperationType.PUSH);

        return defaultService;
    }


}
