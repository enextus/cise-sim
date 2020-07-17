package eu.cise.sim.engine;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.io.DummyMessagePersistence;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.utils.Pair;

public class DefaultMessageProcessor implements MessageProcessor {

    private final SimEngine simEngine;
    private final MessagePersistence messagePersistence;

    public DefaultMessageProcessor(SimEngine simEngine, MessagePersistence messagePersistence) {
        this.simEngine = simEngine;
        this.messagePersistence = messagePersistence;
    }

    public DefaultMessageProcessor(SimEngine simEngine) {
        this.simEngine = simEngine;
        this.messagePersistence = new DummyMessagePersistence();
    }

    @Override
    public Message preview(Message message, SendParam param) {
        return simEngine.prepare(message, param);
    }

    @Override
    public Pair<Acknowledgement, Message> send(Message message, SendParam param) {

        Message preparedMessage = simEngine.prepare(message, param);
        Acknowledgement acknowledgement = simEngine.send(preparedMessage);

        messagePersistence.messageSent(preparedMessage);
        messagePersistence.messageReceived(acknowledgement);

        return new Pair<>(acknowledgement, preparedMessage);
    }

    @Override
    public Acknowledgement receive(Message message) {

        Acknowledgement acknowledgement =  simEngine.receive(message);

        messagePersistence.messageReceived(message);
        messagePersistence.messageSent(acknowledgement);

        return acknowledgement;
    }
}
