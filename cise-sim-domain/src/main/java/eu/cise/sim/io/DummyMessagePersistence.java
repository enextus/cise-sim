package eu.cise.sim.io;

import eu.cise.servicemodel.v1.message.Message;

public class DummyMessagePersistence implements MessagePersistence {

    @Override
    public void messageReceived(Message msgRcv) {
        // do nothing
    }

    @Override
    public void messageSent(Message msgSent) {
        // do nothing
    }
}
