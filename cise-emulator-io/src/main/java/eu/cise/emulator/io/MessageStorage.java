package eu.cise.emulator.io;

/**
 * Temporary persistence of messages.
 */
public interface MessageStorage {
    void store(Object message);
    Object read();
}
