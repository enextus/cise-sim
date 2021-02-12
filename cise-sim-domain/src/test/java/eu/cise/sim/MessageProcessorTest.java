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

package eu.cise.sim;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.DefaultMessageProcessor;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.engine.SimEngine;
import eu.cise.sim.utils.FakeSignatureService;
import eu.cise.sim.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageProcessorTest {

    private SignatureService signatureService;
    private SimConfig config;
    private MessageProcessor messageProcessor;
    private SimEngine engine;
    private SendParam param;

    @Before
    public void before() {
        signatureService = new FakeSignatureService();
        config = mock(SimConfig.class);
        engine = mock(SimEngine.class);
        messageProcessor = new DefaultMessageProcessor(engine);
        param = new SendParam(true, "messageId", "correlationId");
    }

    @Test
    public void it_creates_a_preview_message() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();

        when(engine.prepare(message, param)).thenReturn(message);

        Message previewMessage = messageProcessor.preview(message, param);

        assertThat(previewMessage).isNotNull();
    }

    @Test
    public void it_calls_prepare_from_sim_engine() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();

        messageProcessor.send(message, param);

        verify(engine).prepare(message, param);
    }

    @Test
    public void it_calls_send_from_sim_engine() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        Message preparedMessage = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        preparedMessage.setCorrelationID("new_correlation_id");

        when(engine.prepare(message, param)).thenReturn(preparedMessage);

        messageProcessor.send(message, param);

        verify(engine).send(preparedMessage);
    }

    @Test
    public void it_calls_send_with_success_and_returns_a_pair_containing_the_prepared_message_with_the_acknowledge() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        Push preparedMessage = mock(Push.class);
        Acknowledgement acknowledgement = newAck().build();

        when(engine.prepare(message, param)).thenReturn(preparedMessage);
        when(engine.send(preparedMessage)).thenReturn(acknowledgement);

        Pair<Acknowledgement, Message> sendResponse = messageProcessor.send(message, param);

        assertThat(sendResponse.getA()).isNotNull();
        assertThat(sendResponse.getB()).isEqualTo(preparedMessage);
    }

}
