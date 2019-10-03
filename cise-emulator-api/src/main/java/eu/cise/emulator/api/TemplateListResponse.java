package eu.cise.emulator.api;


import eu.cise.emulator.templates.Template;

import java.util.List;

public class TemplateListResponse {

    private final List<Template> templates;

    public TemplateListResponse(List<Template> templates) {
        this.templates = templates;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public boolean isOk() {
        return true;
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

        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public String getError() {
            return this.errorMessage;
        }
    }
}
