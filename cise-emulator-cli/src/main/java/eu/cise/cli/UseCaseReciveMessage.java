package eu.cise.cli;

import eu.cise.emulator.EmulatorEngine;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public class UseCaseReciveMessage {

    private final EmulatorEngine emulatorEngine;
    private final MessageLoader loader;

    public UseCaseReciveMessage(EmulatorEngine emulatorEngine, MessageLoader loader) {
        this.emulatorEngine = emulatorEngine;
        this.loader = loader;
    }

    public Acknowledgement receive(Message message) {
        var ack = emulatorEngine.receive(message);

        loader.saveSentMessage(message);
        loader.saveReturnedAck(ack);

        return ack;
    }
}
