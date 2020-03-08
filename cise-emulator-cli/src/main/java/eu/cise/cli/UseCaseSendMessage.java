package eu.cise.cli;

import eu.cise.emulator.EmulatorEngine;
import eu.cise.emulator.SendParam;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;

public class UseCaseSendMessage {

    private final EmulatorEngine emulatorEngine;

    public UseCaseSendMessage(EmulatorEngine emulatorEngine) {
        this.emulatorEngine = emulatorEngine;
    }

    public void send() {
        SendParam sendParam = new SendParam(false, "1234", "1234");
        Message preparedMessage = emulatorEngine.prepare(new Push(), sendParam);

        Acknowledgement acknowledgement = emulatorEngine.send(preparedMessage);
    }
}
