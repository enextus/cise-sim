package eu.cise.emulator.api;

import eu.cise.emulator.api.representation.Template;

import java.util.List;

public class TemplateListResponse {

    private final List<Template> templates;

    public List<Template> getTemplates() {
        return templates;
    }

    public TemplateListResponse(List<Template> templates) {
        this.templates = templates;
    }

    public String getError() {
        return "";
    }

    public static class KO extends TemplateListResponse {
        private final String errorMessage;

        public KO(String errorMessage) {
            super(null);
            this.errorMessage = errorMessage;
        }

        public String getError() {
            return this.errorMessage;
        }
    }
}
