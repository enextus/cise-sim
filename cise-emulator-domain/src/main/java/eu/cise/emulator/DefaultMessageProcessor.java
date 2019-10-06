package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public class DefaultMessageProcessor implements MessageProcessor {

    private final EmulatorEngine emulatorEngine;

    public DefaultMessageProcessor(EmulatorEngine emulatorEngine) {
        this.emulatorEngine = emulatorEngine;
    }

    @Override
    public Message preview(Message message, SendParam param) {
        return emulatorEngine.prepare(message, param);
    }

    @Override
    public Acknowledgement send(Message message, SendParam param) {
        Message preparedMessage = emulatorEngine.prepare(message, param);
        return emulatorEngine.send(preparedMessage);
    }

    @Override
    public Acknowledgement receive(Message message) {
        return emulatorEngine.receive(message);
    }
}
