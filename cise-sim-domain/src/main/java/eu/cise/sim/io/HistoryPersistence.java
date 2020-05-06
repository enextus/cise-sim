package eu.cise.sim.io;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.utils.Pair;

import java.util.List;

public interface HistoryPersistence extends MessagePersistence {

    List<Pair<Message, Boolean>> getLatestMessages();
}
