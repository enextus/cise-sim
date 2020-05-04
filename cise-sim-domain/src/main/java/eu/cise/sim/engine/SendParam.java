package eu.cise.sim.engine;

import eu.cise.sim.exceptions.EmptyMessageIdEx;

import java.util.Objects;

import static eu.cise.sim.helpers.Asserts.notBlank;

/**
 * This class is a value object that contains the xml elements
 * to be overridden in the message.
 * <p>
 * The requireAck is a mandatory field
 * The messageId is a mandatory field
 * The correlationId is optional
 */
public class SendParam {

    private final boolean requiresAck;
    private final String messageId;
    private final String correlationId;

    public SendParam(boolean requiresAck, String messageId, String correlationId) {
        this.requiresAck = requiresAck;
        this.messageId = notBlank(messageId, EmptyMessageIdEx.class);
        this.correlationId = correlationId;
    }

    public boolean isRequiresAck() {
        return requiresAck;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendParam sendParam = (SendParam) o;
        return requiresAck == sendParam.requiresAck &&
                Objects.equals(messageId, sendParam.messageId) &&
                Objects.equals(correlationId, sendParam.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requiresAck, messageId, correlationId);
    }
}
