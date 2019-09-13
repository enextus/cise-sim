package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Push;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.*;

/**
 * requestsAck
 */
public class ParamSubstitutionTest {

    private EmulatorEngine engine;

    @Before
    public void before() {
        engine = new DefaultEmulatorEngine();
    }

    @Test
    public void it_checks_nullability_of_SendParam() {
        Push actual = newPush().build();

        assertThatExceptionOfType(SendParamNullEx.class)
                .isThrownBy(() -> engine.prepare(actual, null));
    }

    @Test
    public void it_substitutes_param_requiresAck() {
        Push actual = newPush().isRequiresAck(false).build();

        SendParam paramTrueAck = new SendParam(
                true, "n/a", "n/a");

        Push expected = engine.prepare(actual, paramTrueAck);

        assertThat(expected.isRequiresAck()).isTrue();
    }

    @Test
    public void it_substitutes_param_messageId() {
        Push actual = newPush().id("to-be-overridden").build();

        SendParam paramMsgId = new SendParam(
                false, "new-message-id", "n/a");

        Push expected = engine.prepare(actual, paramMsgId);

        assertThat(expected.getMessageID()).isEqualTo("new-message-id");
    }

    @Test
    public void it_substitutes_param_correlationId() {
        Push actual = newPush().correlationId("to-be-overridden").build();

        SendParam paramCorId = new SendParam(
                false, "n/a", "new-correlation-id");

        Push expected = engine.prepare(actual, paramCorId);

        assertThat(expected.getCorrelationID()).isEqualTo("new-correlation-id");
    }
}
