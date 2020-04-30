package eu.cise.cli;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.engine.SimEngine;

public class UseCaseReciveMessage {

    private final SimEngine simEngine;
    private final MessageLoader loader;

    public UseCaseReciveMessage(SimEngine simEngine, MessageLoader loader) {
        this.simEngine = simEngine;
        this.loader = loader;
    }

    public Acknowledgement receive(Message message) {
        var ack = simEngine.receive(message);

        loader.saveSentMessage(message);
        loader.saveReturnedAck(ack);

        return ack;
    }
}
