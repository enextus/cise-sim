package eu.cise.emulator.api;

import eu.cise.emulator.templates.Template;

import java.io.Serializable;
import java.util.Objects;

public abstract class PreviewResponse implements Serializable {

    private static final long serialVersionUID = 42L;

    private final Template template;
    protected String errorMessage;
    protected boolean ok = true;

    public PreviewResponse(Template template) {
        this.template = template;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isOk() {
        return ok;
    }

    public Template getTemplate() {
        return template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreviewResponse that = (PreviewResponse) o;
        return Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(template);
    }

    public static class OK extends PreviewResponse {
        public OK(Template template) {
            super(template);
            this.errorMessage = null;
        }
    }

    public static class KO extends PreviewResponse {
        public KO(String message) {
            super(null);
            this.ok = false;
            this.errorMessage = message;
        }
    }


}
