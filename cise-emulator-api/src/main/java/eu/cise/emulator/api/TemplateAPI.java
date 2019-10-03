package eu.cise.emulator.api;

import eu.cise.emulator.api.representation.Template;
import eu.cise.emulator.api.representation.TemplateParams;

public class TemplateAPI {

    public PreviewResponse preview(TemplateParams templateParams) {
        return new PreviewResponse.OK(new Template());
    }

}
