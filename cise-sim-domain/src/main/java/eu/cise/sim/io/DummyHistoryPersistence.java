package eu.cise.sim.io;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class DummyHistoryPersistence extends DummyMessagePersistence implements HistoryPersistence {
    @Override
    public List<Pair<Message, Boolean>> getLatestMessages() {
        return new ArrayList<>();
    }
}
