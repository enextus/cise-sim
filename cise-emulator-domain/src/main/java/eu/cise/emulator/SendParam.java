package eu.cise.emulator;

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
