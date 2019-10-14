package eu.cise.emulator;

import eu.cise.emulator.utils.Pair;
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
    public Pair<Acknowledgement, Message> send(Message message, SendParam param) {
        Message preparedMessage = emulatorEngine.prepare(message, param);
        Acknowledgement acknowledgement = emulatorEngine.send(preparedMessage);
        return new Pair<>(acknowledgement, preparedMessage);

    }

    @Override
    public Acknowledgement receive(Message message) {
        return emulatorEngine.receive(message);
    }
}
