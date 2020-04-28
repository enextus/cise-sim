package eu.cise.cli;

import eu.cise.emulator.EmulatorEngine;
import eu.cise.emulator.SendParam;

public class UseCaseSendMessage {

    private final EmulatorEngine emulatorEngine;
    private final MessageLoader loader;

    public UseCaseSendMessage(EmulatorEngine emulatorEngine, MessageLoader loader) {
        this.emulatorEngine = emulatorEngine;
        this.loader = loader;
    }

    public void send(String filename, SendParam sendParam) {
        var message = loader.load(filename);
        var preparedMessage = emulatorEngine.prepare(message, sendParam);
        var acknowledgement = emulatorEngine.send(preparedMessage);

        loader.saveSentMessage(message);
        loader.saveReturnedAck(acknowledgement);
    }
}
