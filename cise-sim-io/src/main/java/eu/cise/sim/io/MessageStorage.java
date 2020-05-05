package eu.cise.sim.io;

/**
 * Temporary persistence of messages.
 */
public interface MessageStorage<T> {

    void store(T message);

    T read();

    boolean delete(T message);
}
