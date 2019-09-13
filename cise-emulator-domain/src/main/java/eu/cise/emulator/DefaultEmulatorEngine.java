package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public class DefaultEmulatorEngine implements EmulatorEngine {

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());
        message.setCorrelationID(param.getCorrelationId());
        return message;
    }

    @Override
    public Acknowledgement send(Message message, SendParam param) {
        return null;
    }

}
