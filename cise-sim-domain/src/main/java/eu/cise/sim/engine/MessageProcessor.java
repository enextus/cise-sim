package eu.cise.sim.engine;

import eu.cise.sim.utils.Pair;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public interface MessageProcessor {
    Message preview(Message message, SendParam param);

    Pair<Acknowledgement, Message> send(Message message, SendParam param);

    Acknowledgement receive(Message message);

}
