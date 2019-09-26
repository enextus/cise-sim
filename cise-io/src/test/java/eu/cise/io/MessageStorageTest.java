package eu.cise.io;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageStorageTest {

    @Test
    public void it_saves_the_value_to_store() {
        Object object = new Object();
        DefaultMessageStorage messageStore = new DefaultMessageStorage();
        messageStore.store(object);

        assertFalse(messageStore.isObjectNull());
    }

    @Test
    public void it_reads_the_value_from_store() {
        Object object = new Object();
        DefaultMessageStorage messageStore = new DefaultMessageStorage();
        messageStore.store(object);

        assertEquals(messageStore.read(), object);
    }
}