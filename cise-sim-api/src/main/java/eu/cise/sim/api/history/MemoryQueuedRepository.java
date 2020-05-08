package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.io.HistoryPersistence;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.io.QueueMessageStorage;
import eu.cise.sim.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class MemoryQueuedRepository implements HistoryPersistence {

    private static final Logger  LOGGER = LoggerFactory.getLogger(MemoryQueuedRepository.class);

    public static final Boolean MSG_SENT = Boolean.TRUE;
    public static final Boolean MSG_RECV = Boolean.FALSE;

    private final MessageStorage<Pair<Message, Boolean>> historyMessageStorage;


    public MemoryQueuedRepository() {
        this.historyMessageStorage = new QueueMessageStorage<>();
    }

    @Override
    public void messageReceived(Message msgRecv) {
        historyMessageStorage.store(new Pair<>(msgRecv, MSG_RECV));
        LOGGER.info("messageReceived");
    }

    @Override
    public void messageSent(Message msgSent) {
        historyMessageStorage.store(new Pair<>(msgSent, MSG_SENT));
        LOGGER.info("messageSent");
    }

    @Override
    public List<Pair<Message, Boolean>> getLatestMessages() {

        List<Pair<Message, Boolean>> messagePairList = new ArrayList<>();

        Pair<Message, Boolean> messagePair;
        while ((messagePair = historyMessageStorage.read()) != null) {
            if (historyMessageStorage.delete(messagePair)) {
                messagePairList.add(messagePair);
            }
        }
        return  messagePairList;
    }
}
