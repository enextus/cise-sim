package eu.cise.sim.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueMessageStorage implements MessageStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessageStorage.class);
    private final Queue<Object> queue;

    public QueueMessageStorage() {
        queue = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void store(Object message) {
        queue.offer(message);
        LOGGER.info("QueueMessageStorage : added message size " + queue.size());
    }

    @Override
    public Object read() {
        return queue.peek();
    }

    @Override
    public boolean delete(Object message) {
        boolean result = queue.remove(message);
        LOGGER.info("QueueMessageStorage : delete message [" + result + "] size " + queue.size());
        return result;
    }
}
