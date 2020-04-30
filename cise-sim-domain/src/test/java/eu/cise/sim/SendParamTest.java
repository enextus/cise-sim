package eu.cise.sim;

import eu.cise.sim.engine.SendParam;
import eu.cise.sim.exceptions.EmptyMessageIdEx;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class SendParamTest {
    @Test
    public void it_checks_messageId_not_null() {

        assertThatExceptionOfType(EmptyMessageIdEx.class)
                .isThrownBy(() -> new SendParam(false, null, "n/a"))
                .withMessageContaining("empty");
    }
}
