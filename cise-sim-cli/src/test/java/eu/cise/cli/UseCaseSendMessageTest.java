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

package eu.cise.cli;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.engine.SimEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UseCaseSendMessageTest {
    private UseCaseSendMessage useCaseSendMessage;
    private SimEngine simEngine;
    private MessageLoader loader;
    private Push messageLoaded;
    private Acknowledgement returnedAck;
    private Message preparedMessage;

    @Before
    public void before() {
        simEngine = mock(SimEngine.class);
        loader = mock(MessageLoader.class);

        useCaseSendMessage = new UseCaseSendMessage(simEngine, loader, null);

        messageLoaded = new Push();
        preparedMessage = new Push();
        returnedAck = new Acknowledgement();

        when(loader.load(anyString())).thenReturn(messageLoaded);
        when(simEngine.prepare(any(),any())).thenReturn(preparedMessage);
        when(simEngine.send(any())).thenReturn(returnedAck);
    }

    @After
    public void after() {
        reset(loader);
        reset(simEngine);
    }

    @Test
    public void it_loads_a_message_from_disk() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(simEngine).prepare(messageLoaded, sendParam);
    }

    @Test
    public void it_sends_a_prepared_message() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(simEngine).send(preparedMessage);
    }

    @Test
    public void it_saves_the_prepared_message() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(loader).saveSentMessage(preparedMessage);
    }

    @Test
    public void it_saves_the_returned_ack_message() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(loader).saveReturnedAck(returnedAck);
    }
}
