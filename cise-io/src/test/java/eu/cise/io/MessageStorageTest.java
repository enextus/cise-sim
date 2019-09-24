package eu.cise.io;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MessageStorageTest {

    @Test
    public void it_save_the_value_to_store() {
        Object object = new Object();
        DefaultMessageStore messageStore = new DefaultMessageStore();
        messageStore.store(object);

        assertFalse(messageStore.isObjectNull());
    }


}