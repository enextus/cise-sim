/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

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
        MessageStorage<Object> messageStore = new QueueMessageStorage<Object>();
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