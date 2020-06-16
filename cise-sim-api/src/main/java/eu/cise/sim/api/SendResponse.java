package eu.cise.sim.api;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.sim.api.dto.MessageBodyAckDto;
import org.glassfish.pfl.basic.logex.Message;

import java.util.Objects;

// TODO check if this class is still necessary ??
public abstract class SendResponse {

    private final MessageBodyAckDto contents;
    protected String errorMessage;
    protected boolean ok = true;

    private Message message;
    private Acknowledgement acknowledgement;

    public SendResponse(MessageBodyAckDto contents) {
        this.contents = contents;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isOk() {
        return ok;
    }

    public MessageBodyAckDto getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendResponse that = (SendResponse) o;
        return Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents);
    }

    public static class OK extends SendResponse {
        public OK(MessageBodyAckDto contents) {
            super(contents);
            this.errorMessage = null;
        }
    }

    public static class KO extends SendResponse {
        public KO(String message) {
            super(null);
            this.ok = false;
            this.errorMessage = message;
        }
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Acknowledgement getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(Acknowledgement acknowledgement) {
        this.acknowledgement = acknowledgement;
    }
}
