package eu.cise.sim.api.dto;

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

    private MessageShortInfoDto(String id, long dateTime, String messageType, String serviceType, boolean isSent, String messageId, String correlationId) {
        this.id = id;
        this.dateTime = dateTime;
        this.messageType = messageType;
        this.serviceType = serviceType;
        this.isSent = isSent;
        this.messageId = messageId;
        this.correlationId = correlationId;
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

        MessageShortInfoDto instance = new MessageShortInfoDto(uuid, dateTime, messageTypeName, serviceType, isSent, ciseMessage.getMessageID(), ciseMessage.getCorrelationID());

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
}
