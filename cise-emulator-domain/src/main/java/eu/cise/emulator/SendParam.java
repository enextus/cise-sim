package eu.cise.emulator;

/**
 * This class is a value object that contains the xml elements
 * to be overridden in the message.
 *
 * The requireAck is a mandatory field
 * The messageId is a mandatory field
 * The correlationId is optional
 */
public class SendParam {

    private boolean requireAck;
    private String messageId;
    private String correlationId;

    public SendParam(boolean requireAck, String messageId, String correlationId) {
        this.requireAck = requireAck;
        this.messageId = messageId;
        this.correlationId = correlationId;
    }

    public boolean isRequireAck() {
        return requireAck;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
