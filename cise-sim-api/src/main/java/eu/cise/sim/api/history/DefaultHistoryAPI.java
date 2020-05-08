package eu.cise.sim.api.history;

import java.util.List;

public class DefaultHistoryAPI implements HistoryAPI {

    private final MemoryQueuedRepository memoryQueuedRepository;

    public DefaultHistoryAPI(MemoryQueuedRepository messageRepository) {
        this.memoryQueuedRepository = messageRepository;
    }

    public List<MessageShortInfoDto> getLatestMessages() {
        return memoryQueuedRepository.getLatestMessages();
    }
}
