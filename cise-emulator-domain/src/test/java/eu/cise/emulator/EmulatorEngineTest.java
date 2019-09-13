package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Push;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * requestAck
 */
public class EmulatorEngineTest {

    private EmulatorEngine engine;

    @Before
    public void before() {
        engine = new DefaultEmulatorEngine();
    }

    @Test
    public void it_substitutes_the_requiresAck() {
        Push actual = newPush().isRequiresAck(false).build();

        SendParam paramTrueAck = new SendParam(true, "n/a", "n/a");

        Push expected = engine.prepare(actual, paramTrueAck);

        assertThat(expected.isRequiresAck()).isTrue();
    }

    @Test
    public void it_substitutes_the_messageId() {
        Push actual = newPush().id("to-be-overridden").build();

        SendParam paramMsgId = new SendParam(false, "new-message-id", "n/a");

        Push expected = engine.prepare(actual, paramMsgId);

        assertThat(expected.getMessageID()).isEqualTo("new-message-id");
    }
}
