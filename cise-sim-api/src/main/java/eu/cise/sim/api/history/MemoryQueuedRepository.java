package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.io.QueueMessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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
        String uuid = UUID.randomUUID().toString();
        historyMessageStorage.store(MessageShortInfoDto.getInstance(msgRecv, MSG_RECV, new Date(), uuid));
        LOGGER.info("messageReceived");
    }

    @Override
    public void messageSent(Message msgSent) {
        String uuid = UUID.randomUUID().toString();
        historyMessageStorage.store(MessageShortInfoDto.getInstance(msgSent, MSG_SENT, new Date(), uuid));
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
