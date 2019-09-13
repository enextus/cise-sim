package eu.cise.emulator;

import eu.cise.emulator.exceptions.NullSendParamEx;
import eu.cise.servicemodel.v1.message.Push;
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
import static org.mockito.Mockito.when;

/**
 * requestsAck
 */
public class FieldAdaptationTest {

    private EmulatorEngine engine;
    private EmuConfig config;

    @Before
    public void before() {
        config = mock(EmuConfig.class);
        engine = new DefaultEmulatorEngine(
                new FakeSignatureService(),
                clockFiveMay2019(),
                config);
    }

    private Clock clockFiveMay2019() {
        return Clock.fixed(fiveMay2019(), ZoneId.systemDefault());
    }

    @Test
    public void it_checks_nullability_of_SendParam() {
        Push actual = newPush().build();

        assertThatExceptionOfType(NullSendParamEx.class)
                .isThrownBy(() -> engine.prepare(actual, null))
                .withMessageContaining("SendParam");
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

    @Test
    public void it_updates_the_create_date_time() {
        Push actual = newPush().build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getCreationDateTime()).isEqualTo(toXMLGregorianCalendar(dateFiveMay2019()));
    }

    @Test
    public void it_overrides_serviceId() {
        when(config.serviceId()).thenReturn("new-service-id");

        Push actual = newPush().sender(newService().id("to-be-overridden")).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getSender().getServiceID()).isEqualTo("new-service-id");
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
