package eu.cise.emulator.api;

import java.io.Serializable;
import java.util.Objects;

public abstract class SendResponse implements Serializable {

    private static final long serialVersionUID = 42L;

    private final MessageApiDto contents;
    protected String errorMessage;
    protected boolean ok = true;

    public SendResponse(MessageApiDto contents) {
        this.contents = contents;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isOk() {
        return ok;
    }

    public MessageApiDto getContents() {
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
        public OK(MessageApiDto contents) {
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


}
