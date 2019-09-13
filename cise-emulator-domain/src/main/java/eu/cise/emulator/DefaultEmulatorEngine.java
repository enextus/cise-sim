package eu.cise.emulator;

import eu.cise.emulator.exceptions.NullSendParamEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import static eu.cise.emulator.helpers.Asserts.*;

public class DefaultEmulatorEngine implements EmulatorEngine {

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        notNull(param, NullSendParamEx.class);

        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());
        message.setCorrelationID(param.getCorrelationId());

        return message;
    }

    @Override
    public Acknowledgement send(Message message) {
        return null;
    }

}
