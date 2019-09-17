package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.emulator.exceptions.NullSendParamEx;
import eu.cise.emulator.utils.FakeSignatureService;
import eu.cise.servicemodel.v1.message.Push;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static eu.cise.servicemodel.v1.service.ServiceOperationType.PULL;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.PUSH;
import static eu.cise.servicemodel.v1.service.ServiceType.*;
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
    private Dispatcher dispatcher;

    @Before
    public void before() {
        config = mock(EmuConfig.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultEmulatorEngine(
                new FakeSignatureService(),
                config, dispatcher, clockFiveMay2019()
        );
    }

    private Clock clockFiveMay2019() {
        return Clock.fixed(fiveMay2019(), ZoneId.systemDefault());
    }

    @Test
    public void it_checks_nullability_of_SendParam() {
        Push actual = newPush().sender(newService()).build();

        assertThatExceptionOfType(NullSendParamEx.class)
                .isThrownBy(() -> engine.prepare(actual, null))
                .withMessageContaining("SendParam");
    }

    @Test
    public void it_substitutes_param_requiresAck() {
        Push actual = newPush().sender(newService())
                .isRequiresAck(false)
                .build();

        SendParam paramTrueAck = new SendParam(
                true, "n/a", "n/a");

        Push expected = engine.prepare(actual, paramTrueAck);

        assertThat(expected.isRequiresAck()).isTrue();
    }

    @Test
    public void it_substitutes_param_messageId() {
        Push actual = newPush().sender(newService())
                .id("to-be-overridden")
                .build();

        SendParam paramMsgId = new SendParam(
                false, "new-message-id", "n/a");

        Push expected = engine.prepare(actual, paramMsgId);

        assertThat(expected.getMessageID()).isEqualTo("new-message-id");
    }

    @Test
    public void it_substitutes_param_correlationId() {
        Push actual = newPush().sender(newService())
                .correlationId("to-be-overridden")
                .build();

        SendParam paramCorId = new SendParam(
                false, "n/a", "new-correlation-id");

        Push expected = engine.prepare(actual, paramCorId);

        assertThat(expected.getCorrelationID()).isEqualTo("new-correlation-id");
    }

    @Test
    public void it_substitutes_param_correlationId_with_messageId_when_correlationID_is_null() {
        Push actual = newPush().sender(newService())
                .correlationId("to-be-overridden")
                .build();

        SendParam paramCorId = new SendParam(
                false, "messageId", null);

        Push expected = engine.prepare(actual, paramCorId);

        assertThat(expected.getCorrelationID()).isEqualTo("messageId");
    }

    @Test
    public void it_substitutes_param_correlationId_with_messageId_when_correlationID_is_empty() {
        Push actual = newPush().sender(newService())
                .correlationId("to-be-overridden")
                .build();

        SendParam paramCorId = new SendParam(
                false, "messageId", "");

        Push expected = engine.prepare(actual, paramCorId);

        assertThat(expected.getCorrelationID()).isEqualTo("messageId");
    }


    @Test
    public void it_updates_the_create_date_time() {
        Push actual = newPush().sender(newService()).build();

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


    @Test
    public void it_overrides_serviceType() {
        when(config.serviceType()).thenReturn(DOCUMENT_SERVICE);

        Push actual = newPush().sender(newService().type(CARGO_SERVICE)).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getSender().getServiceType()).isEqualTo(DOCUMENT_SERVICE);
    }

    @Test
    public void it_overrides_serviceOperation() {
        when(config.serviceOperation()).thenReturn(PUSH);

        Push actual = newPush().sender(newService().operation(PULL)).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getSender().getServiceOperation()).isEqualTo(PUSH);
    }

    @Test
    public void it_doesnt_override_serviceId_for_null_value_in_config() {
        when(config.serviceId()).thenReturn(null);

        Push actual = newPush().sender(newService().id("not-to-be-overridden")).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getSender().getServiceID()).isEqualTo("not-to-be-overridden");
    }

    @Test
    public void it_doesnt_override_serviceId_for_empty_value_in_config() {
        when(config.serviceId()).thenReturn("");

        Push actual = newPush().sender(newService().id("not-to-be-overridden")).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getSender().getServiceID()).isEqualTo("not-to-be-overridden");
    }

    @Test
    public void it_doesnt_override_serviceType_for_null_value_in_config() {
        when(config.serviceType()).thenReturn(null);

        Push actual = newPush().sender(newService().type(VESSEL_SERVICE)).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getSender().getServiceType()).isEqualTo(VESSEL_SERVICE);
    }

    @Test
    public void it_doesnt_override_serviceOperation_for_null_value_in_config() {
        when(config.serviceOperation()).thenReturn(null);

        Push actual = newPush().sender(newService().operation(PUSH)).build();

        Push expected = engine.prepare(actual, params());

        assertThat(expected.getSender().getServiceOperation()).isEqualTo(PUSH);
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
