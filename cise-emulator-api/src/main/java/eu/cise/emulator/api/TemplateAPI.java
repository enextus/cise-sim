package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.representation.TemplateParams;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Message;

public class TemplateAPI {

    private final MessageProcessor messageProcessor;
    private final TemplateLoader templateLoader;

    public TemplateAPI(MessageProcessor messageProcessor, TemplateLoader templateLoader) {
        this.messageProcessor = messageProcessor;
        this.templateLoader = templateLoader;
    }

    public PreviewResponse preview(TemplateParams templateParams) {
        Template template = templateLoader.loadTemplate(templateParams.getTemplateId());
        Message message = messageProcessor.preview(template.getTemplateContent(), null);
        return new PreviewResponse.OK(new Template());
    }

    public TemplateListResponse getTemplates() {
        try {
            return new TemplateListResponse.OK(templateLoader.loadTemplateList());
        } catch (Exception e) {
            return new TemplateListResponse.KO(e.getMessage());
        }
    }

}
