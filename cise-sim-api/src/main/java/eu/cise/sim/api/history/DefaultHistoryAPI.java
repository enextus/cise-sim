package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.io.HistoryPersistence;
import eu.cise.sim.utils.Pair;

import java.util.List;

public class DefaultHistoryAPI implements HistoryAPI {

    private final HistoryPersistence memoryQueuedRepository;

    public DefaultHistoryAPI(HistoryPersistence messageRepository) {
        this.memoryQueuedRepository = messageRepository;
    }

    public List<Pair<Message, Boolean>> getLatestMessages() {
        return memoryQueuedRepository.getLatestMessages();
    }
}
