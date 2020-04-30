package eu.cise.cli;

import eu.cise.sim.engine.SimEngine;
import eu.cise.sim.engine.SendParam;

public class UseCaseSendMessage {

    private final SimEngine simEngine;
    private final MessageLoader loader;

    public UseCaseSendMessage(SimEngine simEngine, MessageLoader loader) {
        this.simEngine = simEngine;
        this.loader = loader;
    }

    public void send(String filename, SendParam sendParam) {
        var message = loader.load(filename);
        var preparedMessage = simEngine.prepare(message, sendParam);
        var acknowledgement = simEngine.send(preparedMessage);

        loader.saveSentMessage(message);
        loader.saveReturnedAck(acknowledgement);
    }
}
