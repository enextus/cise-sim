package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Push;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * requestAck
 */
public class EmulatorEngineTest {


    @Test
    public void it_substitutes_the_requireAck() {
        EmulatorEngine engine = new DefaultEmulatorEngine();

        Push actual = newPush().build();

        SendParam paramTrueAck = new SendParam(true, "id", "id");

        Push expected = (Push) engine.prepare(actual, paramTrueAck);

        assertThat(expected.isRequiresAck()).isTrue();

    }
}
