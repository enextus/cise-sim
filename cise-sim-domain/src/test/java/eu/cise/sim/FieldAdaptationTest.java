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

import eu.cise.servicemodel.v1.message.Push;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.DefaultSimEngine;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.engine.SimEngine;
import eu.cise.sim.exceptions.NullSendParamEx;
import eu.cise.sim.utils.FakeSignatureService;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

/**
 * requestsAck
 */
public class FieldAdaptationTest {

    private SimEngine engine;
    private SimConfig config;
    private Dispatcher dispatcher;

    @Before
    public void before() {
        config = mock(SimConfig.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultSimEngine(
                new FakeSignatureService(),
                config, dispatcher, clockFiveMay2019()
        );
    }

    private Clock clockFiveMay2019() {
        return Clock.fixed(fiveMay2019(), ZoneId.systemDefault());
    }

    @Test
    public void it_checks_nullability_of_SendParam() {
        Push actual = newPush().sender(newService()).build();

        assertThatExceptionOfType(NullSendParamEx.class)
                .isThrownBy(() -> engine.prepare(actual, null))
                .withMessageContaining("SendParam");
    }

    @Test
    public void it_substitutes_param_requiresAck() {
        Push actual = newPush().sender(newService())
                .isRequiresAck(false)
                .build();

        SendParam paramTrueAck = new SendParam(
                true, "n/a", "n/a");

        Push expected = engine.prepare(actual, paramTrueAck);

        assertThat(expected.isRequiresAck()).isTrue();
    }

    @Test
    public void it_substitutes_param_messageId() {
        Push actual = newPush().sender(newService())
                .id("to-be-overridden")
                .build();

        SendParam paramMsgId = new SendParam(
                false, "new-message-id", "n/a");

        Push expected = engine.prepare(actual, paramMsgId);

        assertThat(expected.getMessageID()).isEqualTo("new-message-id");
    }

    @Test
    public void it_substitutes_param_correlationId() {
        Push actual = newPush().sender(newService())
                .correlationId("to-be-overridden")
                .build();

        SendParam paramCorId = new SendParam(
                false, "messageId", "new-correlation-id");

        Push expected = engine.prepare(actual, paramCorId);

        assertThat(expected.getCorrelationID()).isEqualTo("new-correlation-id");
    }

    @Test
    public void it_substitutes_param_correlationId_with_messageId_when_correlationID_is_null() {
        Push actual = newPush().sender(newService())
                .correlationId("to-be-overridden")
                .build();

        SendParam paramCorId = new SendParam(
                false, "messageId", null);

        Push expected = engine.prepare(actual, paramCorId);

        assertThat(expected.getCorrelationID()).isEqualTo("messageId");
    }

    @Test
    public void it_substitutes_param_correlationId_with_messageId_when_correlationID_is_empty() {
        Push actual = newPush().sender(newService())
                .correlationId("to-be-overridden")
                .build();

        SendParam paramCorId = new SendParam(
                false, "messageId", "");

        Push expected = engine.prepare(actual, paramCorId);

        assertThat(expected.getCorrelationID()).isEqualTo("messageId");
    }


    @Test
    public void it_updates_the_create_date_time() {
        Push actual = newPush().sender(newService()).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getCreationDateTime()).isEqualTo(toXMLGregorianCalendar(dateFiveMay2019()));
    }



    private SendParam params() {
        return new SendParam(false, "n/a", "n/a");
    }

    private Date dateFiveMay2019() {
        return Date.from(fiveMay2019());
    }

    private Instant fiveMay2019() {
        return Instant.parse("2019-05-18T17:00:00.00Z");
    }
}
