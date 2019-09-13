package eu.cise.emulator;

import eu.cise.emulator.exceptions.NullSenderEx;
import eu.cise.servicemodel.v1.message.Push;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

public class EnginePreconditionsTest {

    private EmulatorEngine engine;
    private SignatureService signature;
    private Push push;

    @Before
    public void before() {
        EmuConfig config = ConfigFactory.create(EmuConfig.class);
        signature = mock(SignatureService.class);
        engine = new DefaultEmulatorEngine(signature, config);
        push = newPush().build();

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
