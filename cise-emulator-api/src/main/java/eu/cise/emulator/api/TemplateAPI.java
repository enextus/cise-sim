package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.representation.Template;
import eu.cise.emulator.api.representation.TemplateParams;
import eu.cise.servicemodel.v1.message.Message;

public class TemplateAPI {

    private MessageProcessor messageProcessor;

    public TemplateAPI(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }


    public PreviewResponse preview(TemplateParams templateParams) {
        Message message = messageProcessor.preview(null, null);
        return new PreviewResponse.OK(new Template());
    }

}
