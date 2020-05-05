package io;

import eu.cise.sim.io.DefaultMessageStorage;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.io.QueueMessageStorage;
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


    @Test
    public void it_queue_saves_and_read_the_values_to_store() {
        Object objectInA = new Object();
        Object objectInB = new Object();
        MessageStorage messageStore = new QueueMessageStorage();
        messageStore.store(objectInA);
        messageStore.store(objectInB);

        Object objectRcvA = messageStore.read();
        assertEquals(objectInA, objectRcvA);

        Object objectRcvB = messageStore.read();
        assertNotEquals(objectInB, objectRcvB);

        boolean isDel = messageStore.delete(objectInA);
        assertTrue(isDel);

        objectRcvB= messageStore.read();
        assertEquals(objectInB, objectRcvB);

    }
}