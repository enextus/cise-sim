package eu.cise.sim.engine;

import eu.cise.sim.utils.Pair;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public class DefaultMessageProcessor implements MessageProcessor {

    private final SimEngine simEngine;

    public DefaultMessageProcessor(SimEngine simEngine) {
        this.simEngine = simEngine;
    }

    @Override
    public Message preview(Message message, SendParam param) {
        return simEngine.prepare(message, param);
    }

    @Override
    public Pair<Acknowledgement, Message> send(Message message, SendParam param) {
        Message preparedMessage = simEngine.prepare(message, param);
        Acknowledgement acknowledgement = simEngine.send(preparedMessage);
        return new Pair<>(acknowledgement, preparedMessage);
    }

    @Override
    public Acknowledgement receive(Message message) {
        return simEngine.receive(message);
    }
}
