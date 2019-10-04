package eu.cise.emulator.api.representation;

import eu.cise.emulator.SendParam;

import java.util.Objects;

public class TemplateParams {
    private final String templateId;
    private final String messageId;
    private final String correlationId;
    private final boolean requestAck;

    public TemplateParams(String templateId, String messageId, String correlationId, boolean requestAck) {

        this.templateId = templateId;
        this.messageId = messageId;
        this.correlationId = correlationId;
        this.requestAck = requestAck;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public boolean isRequestAck() {
        return requestAck;
    }

    public SendParam getSendParams() {
        SendParam sendParam = new SendParam(this.requestAck, this.messageId, this.correlationId);
        return sendParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateParams that = (TemplateParams) o;
        return requestAck == that.requestAck &&
                templateId.equals(that.templateId) &&
                messageId.equals(that.messageId) &&
                Objects.equals(correlationId, that.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateId, messageId, correlationId, requestAck);
    }
}
