package eu.cise.io;

/**
 * Temporary persistence of messages.
 */
public interface MessageStorage {
    void store(Object message);
    Object read();
}
