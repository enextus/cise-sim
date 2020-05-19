package eu.cise.sim.io;

import eu.cise.servicemodel.v1.message.Message;

public interface MessagePersistence {

    void messageReceived(Message msgRcv);

    void messageSent(Message msgSent);
}
