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
import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.DefaultSimEngine;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.engine.SimEngine;
import eu.cise.sim.exceptions.*;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

public class EnginePreconditionsTest {

    private SimEngine engine;
    private SignatureService signature;
    private Push push;
    private SimConfig config;
    private Clock clock;
    private Dispatcher dispatcher;

    @Before
    public void before() {
        config = ConfigFactory.create(SimConfig.class);
        signature = mock(SignatureService.class);
        clock = Clock.systemUTC();
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultSimEngine(signature, config, dispatcher, clock);
        push = newPush().build();

    }

    @Test
    public void it_must_have_a_signature_service_not_null() {
        assertThatExceptionOfType(NullSignatureServiceEx.class)
                .isThrownBy(() -> new DefaultSimEngine(null, config, dispatcher, clock))
                .withMessageContaining("signature");
    }

    @Test
    public void it_must_have_a_config_not_null() {
        assertThatExceptionOfType(NullConfigEx.class)
                .isThrownBy(() -> new DefaultSimEngine(signature, null, dispatcher, clock))
                .withMessageContaining("config");
    }

    @Test
    public void it_must_have_a_clock_not_null() {
        assertThatExceptionOfType(NullClockEx.class)
                .isThrownBy(() -> new DefaultSimEngine(signature, config, dispatcher, null))
                .withMessageContaining("clock");
    }

    @Test
    public void it_must_have_a_dispatcher_not_null() {
        assertThatExceptionOfType(NullDispatcherEx.class)
                .isThrownBy(() -> new DefaultSimEngine(signature, config, null, clock))
                .withMessageContaining("dispatcher");
    }

    @Test
    public void it_must_have_a_sender() {
        assertThatExceptionOfType(NullSenderEx.class)
                .isThrownBy(() -> engine.prepare(push, params()))
                .withMessageContaining("sender");
    }

    private SendParam params() {
        return new SendParam(false, "n/a", "n/a");
    }

}
