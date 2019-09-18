package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.emulator.exceptions.*;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.signature.SignatureService;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

public class EnginePreconditionsTest {

    private EmulatorEngine engine;
    private SignatureService signature;
    private Push push;
    private EmuConfig config;
    private Clock clock;
    private Dispatcher dispatcher;

    @Before
    public void before() {
        config = ConfigFactory.create(EmuConfig.class);
        signature = mock(SignatureService.class);
        clock = Clock.systemUTC();
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultEmulatorEngine(signature, config, dispatcher, clock);
        push = newPush().build();

    }

    @Test
    public void it_must_have_a_signature_service_not_null() {
        assertThatExceptionOfType(NullSignatureServiceEx.class)
                .isThrownBy(() -> new DefaultEmulatorEngine(null, config, dispatcher, clock))
                .withMessageContaining("signature");
    }

    @Test
    public void it_must_have_a_config_not_null() {
        assertThatExceptionOfType(NullConfigEx.class)
                .isThrownBy(() -> new DefaultEmulatorEngine(signature, null, dispatcher, clock))
                .withMessageContaining("config");
    }

    @Test
    public void it_must_have_a_clock_not_null() {
        assertThatExceptionOfType(NullClockEx.class)
                .isThrownBy(() -> new DefaultEmulatorEngine(signature, config, dispatcher, null))
                .withMessageContaining("clock");
    }

    @Test
    public void it_must_have_a_dispatcher_not_null() {
        assertThatExceptionOfType(NullDispatcherEx.class)
                .isThrownBy(() -> new DefaultEmulatorEngine(signature, config, null, clock))
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
