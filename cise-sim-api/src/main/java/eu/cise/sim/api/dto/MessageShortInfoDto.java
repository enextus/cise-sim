package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class MessageShortInfoDto implements Serializable {

    private static final long serialVersionUID = 42L;


    private final String id;
    private final long dateTime;
    private final String messageType;
    private final String serviceType;
    private final boolean isSent;

    // Message Thread Infos
    private final String messageId;
    private final String correlationId;

    // Sender and receiver
    private final String from;
    private final String to;

    // If Ack Synch message,
    private final String ackResult;

    private MessageShortInfoDto(String id, long dateTime, String messageType, String serviceType, boolean isSent, String messageId, String correlationId, String from, String to, String ackType) {
        this.id = id;
        this.dateTime = dateTime;
        this.messageType = messageType;
        this.serviceType = serviceType;
        this.isSent = isSent;
        this.messageId = messageId;
        this.correlationId = correlationId;
        this.from = from;
        this.to = to;
        this.ackResult = ackType;
    }

    public static MessageShortInfoDto getInstance(Message ciseMessage, boolean isSent, Date timestamp, String uuid) throws IllegalArgumentException {

        long  dateTime = timestamp.getTime();

        MessageTypeEnum messageType = MessageTypeEnum.valueOf(ciseMessage);
        String messageTypeName = messageType.getUiName();

        String  serviceType = "";
        if (messageType != MessageTypeEnum.ACK_ASYNC && messageType != MessageTypeEnum.ACK_SYNC) {
            serviceType = ciseMessage.getSender().getServiceType().value();
            if (StringUtils.isEmpty(serviceType)) {
                throw new IllegalArgumentException("Service type is empty");
            }
        }

        String ackResult = "";
        if (messageType == MessageTypeEnum.ACK_SYNC) {
            AcknowledgementType ackType = ((Acknowledgement) ciseMessage).getAckCode();
            ackResult = ackType.value();
        }

        // Sender & Recipient
        String from = ciseMessage.getSender() != null ? ciseMessage.getSender().getServiceID() : "";
        String to = ciseMessage.getRecipient() != null ? ciseMessage.getRecipient().getServiceID() : "";


        MessageShortInfoDto instance = new MessageShortInfoDto(uuid, dateTime, messageTypeName, serviceType, isSent, ciseMessage.getMessageID(), ciseMessage.getCorrelationID(), from, to, ackResult);

        check(instance);

        return instance;
    }

    private static void check(MessageShortInfoDto instance) throws IllegalArgumentException {

        if (StringUtils.isEmpty(instance.getId())) {
            throw new IllegalArgumentException("MessageID is empty");
        }

        if (StringUtils.isEmpty(instance.getMessageType())) {
            throw new IllegalArgumentException("Type of Message is empty");
        }
    }

    public String getId() {
        return id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public boolean isSent() {
        return isSent;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAckResult() {
        return ackResult;
    }
}
