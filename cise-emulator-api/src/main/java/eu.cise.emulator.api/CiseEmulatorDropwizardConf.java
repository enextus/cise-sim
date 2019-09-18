package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import io.dropwizard.Configuration;

public class CiseEmulatorDropwizardConf extends Configuration {

    private MessageProcessor messageProcessor;

    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }
}
