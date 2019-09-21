package eu.cise.emulator;

import eu.cise.emulator.exceptions.NullMessageIdEx;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class SendParamTest {
    @Test
    public void it_checks_messageId_not_null() {

        assertThatExceptionOfType(NullMessageIdEx.class)
                .isThrownBy(() -> new SendParam(false, null, "n/a"))
                .withMessageContaining("SendParam");
    }
}
