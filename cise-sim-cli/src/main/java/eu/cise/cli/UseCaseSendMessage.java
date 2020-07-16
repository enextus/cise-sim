package eu.cise.cli;

import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.engine.SimEngine;

public class UseCaseSendMessage {

    private final MessageProcessor messageProcessor;

    private final SimEngine simEngine;
    private final MessageLoader loader;

    public UseCaseSendMessage(SimEngine simEngine, MessageLoader loader, MessageProcessor messageProcessor) {
        this.simEngine = simEngine;
        this.loader = loader;
        this.messageProcessor = messageProcessor;
    }

    public void send(String filename, SendParam sendParam) {
        if (simEngine != null) {
            sendOriginal(filename, sendParam);
        } else {
            sendWithProcessor(filename, sendParam);
        }
    }

    public void sendWithProcessor(String filename, SendParam sendParam) {
        var message = loader.load(filename);
        messageProcessor.send(message, sendParam);
    }

    public void sendOriginal(String filename, SendParam sendParam) {
        var message = loader.load(filename);
        var preparedMessage = simEngine.prepare(message, sendParam);
        var acknowledgement = simEngine.send(preparedMessage);

        loader.saveSentMessage(message);
        loader.saveReturnedAck(acknowledgement);
    }
}
