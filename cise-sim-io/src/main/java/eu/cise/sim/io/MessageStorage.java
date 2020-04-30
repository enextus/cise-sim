package eu.cise.sim.io;

/**
 * Temporary persistence of messages.
 */
public interface MessageStorage {
    void store(Object message);

    Object read();

    boolean delete(Object message);
}
