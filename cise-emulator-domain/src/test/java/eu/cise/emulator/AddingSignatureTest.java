package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Push;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddingSignatureTest {

    private EmulatorEngine engine;
    private SignatureService signature;
    private Push push;

    @Before
    public void before() {
        signature = mock(SignatureService.class);
        engine = new DefaultEmulatorEngine(signature);
        push = newPush().build();
    }

    @Test
    public void it_signs_the_message() {
        engine.prepare(push, params());

        verify(signature).sign(push);
    }

    private SendParam params() {
        return new SendParam(false, "msgId", "corrId");
    }
}
