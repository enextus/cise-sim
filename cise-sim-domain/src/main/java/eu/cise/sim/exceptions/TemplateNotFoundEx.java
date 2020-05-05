package eu.cise.sim.exceptions;

public class TemplateNotFoundEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The template id specified can not be found. templateId: ";

    public TemplateNotFoundEx(String templateId) {
        super(EXCEPTION_MESSAGE + safe(templateId));
    }

    private static String safe(String templateId) {
        return templateId == null ? "is null" : templateId;
    }
}
