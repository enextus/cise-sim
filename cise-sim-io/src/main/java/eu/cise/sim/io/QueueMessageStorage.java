package eu.cise.sim.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueMessageStorage<T> implements MessageStorage<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessageStorage.class);
    private final Queue<T> queue;

    public QueueMessageStorage() {
        queue = new ConcurrentLinkedDeque<T>();
    }

    @Override
    public void store(T message) {
        queue.offer(message);
        LOGGER.info("store message, size " + queue.size());
    }

    @Override
    public T read() {
        return queue.peek();
    }

    @Override
    public boolean delete(T message) {
        boolean result = queue.remove(message);
        LOGGER.info("delete message [" + result + "], size " + queue.size());
        return result;
    }
}
