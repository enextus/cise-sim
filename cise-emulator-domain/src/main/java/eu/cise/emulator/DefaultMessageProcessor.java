package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Message;

public class DefaultMessageProcessor implements MessageProcessor {
    @Override
    public Message preview(Message message, SendParam param) {
        return null;
    }
}
