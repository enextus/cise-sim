package eu.cise.emulator.api;


import eu.cise.emulator.templates.Template;
import java.util.ArrayList;
import java.util.List;

public interface TemplateListResponse {


    List<Template> getTemplates();

    boolean isOk();

    String getError();

    class OK implements TemplateListResponse {

        private final List<Template> templates;

        public OK(List<Template> templates) {
            this.templates = templates;
        }

        @Override
        public List<Template> getTemplates() {
            return templates;
        }

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public String getError() {
            return "";
        }
    }

    class KO implements TemplateListResponse {
        private final String errorMessage;

        public KO(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public List<Template> getTemplates() {
            return new ArrayList<>();
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
