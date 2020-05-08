package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.io.QueueMessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class MemoryQueuedRepository implements MessagePersistence {

    private static final Logger  LOGGER = LoggerFactory.getLogger(MemoryQueuedRepository.class);

    public static final Boolean MSG_SENT = Boolean.TRUE;
    public static final Boolean MSG_RECV = Boolean.FALSE;

    private final MessageStorage<MessageShortInfoDto> historyMessageStorage;


    public MemoryQueuedRepository() {
        this.historyMessageStorage = new QueueMessageStorage<>();
    }

    @Override
    public void messageReceived(Message msgRecv) {
        historyMessageStorage.store(MessageShortInfoDto.getInstance(msgRecv, MSG_RECV));
        LOGGER.info("messageReceived");
    }

    @Override
    public void messageSent(Message msgSent) {
        historyMessageStorage.store(MessageShortInfoDto.getInstance(msgSent, MSG_SENT));
        LOGGER.info("messageSent");
    }

    public List<MessageShortInfoDto> getLatestMessages() {

        List<MessageShortInfoDto> messagePairList = new ArrayList<>();

        MessageShortInfoDto messagePair;
        while ((messagePair = historyMessageStorage.read()) != null) {
            if (historyMessageStorage.delete(messagePair)) {
                messagePairList.add(messagePair);
            }
        }
        return  messagePairList;
    }
}
