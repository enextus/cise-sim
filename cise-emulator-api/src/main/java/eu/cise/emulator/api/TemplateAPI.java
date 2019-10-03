package eu.cise.emulator.api;

import eu.cise.emulator.api.helpers.TemplateLoader;
import eu.cise.emulator.api.representation.TemplateParams;

public class TemplateAPI {
    private final TemplateLoader templateLoader;

    public TemplateAPI(TemplateLoader templateLoader) {
        this.templateLoader = templateLoader;
    }

    public PreviewResponse preview(TemplateParams templateParams) {
        return null;
    }

    public TemplateListResponse getTemplates() {
        return new TemplateListResponse(templateLoader.loadTemplateList());
    }
}
