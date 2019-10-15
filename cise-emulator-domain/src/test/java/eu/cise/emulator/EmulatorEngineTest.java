package eu.cise.emulator;


import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.exceptions.*;
import eu.cise.emulator.utils.Scenarios;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static eu.cise.servicemodel.v1.message.AcknowledgementType.SERVICE_TYPE_NOT_SUPPORTED;
import static eu.cise.servicemodel.v1.message.AcknowledgementType.SUCCESS;
import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmulatorEngineTest {

    private EmuConfig config;
    private EmulatorEngine engine;
    private Dispatcher dispatcher;
    private Push message;
    private XmlMapper prettyNotValidatingXmlMapper;

    @Before
    public void before() {
        config = mock(EmuConfig.class);
        dispatcher = mock(Dispatcher.class);
        prettyNotValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        engine = new DefaultEmulatorEngine(mock(SignatureService.class), dispatcher, config, prettyNotValidatingXmlMapper);
        message = newPush()
                .id("aMessageId")
                .sender(newService().id("aServiceId").type(VESSEL_SERVICE))
                .build();

        when(config.serviceId()).thenReturn("myServiceId");
        when(config.serviceType()).thenReturn(VESSEL_SERVICE);
        when(config.endpointUrl()).thenReturn("endpointUrl");
    }

    @After
    public void after() {
        reset(config);
    }

    @Test
    public void it_sends_message_successfully() {
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(
                new DispatchResult(true, Scenarios.getSyncAckMsgSuccess()));

        engine.send(message);

        verify(dispatcher).send(message, "endpointUrl");
    }

    @Test
    public void it_sends_a_message_failing_the_dispatch_for_end_point_not_found() {
        when(dispatcher.send(message, "endpointUrl")).thenThrow(DispatcherException.class);

        assertThatExceptionOfType(EndpointNotFoundEx.class)
                .isThrownBy(() -> engine.send(message))
                .withMessageContaining("endpoint not found");
    }

    @Test
    public void it_sends_a_message_getting_a_successful_response_and_returns_the_acknowledge() {
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(
                new DispatchResult(true, Scenarios.getSyncAckMsgSuccess()));

        assertThat(engine.send(message).getAckCode()).isEqualTo(SUCCESS);
    }

    @Test
    public void it_sends_a_message_getting_an_unsuccessful_response() {
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(
                new DispatchResult(false, ""));

        assertThatExceptionOfType(EndpointErrorEx.class)
                .isThrownBy(() -> engine.send(message))
                .withMessageContaining("endpoint returned an error");
    }

    @Test
    public void it_adds_a_sender_to_an_ack_received_without_it() {
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(
                new DispatchResult(true, Scenarios.getSyncAckMsgSuccessNoSender()));

        Acknowledgement ack = engine.send(message);

        assertThat(ack.getSender()).isNotNull();
        assertThat(ack.getSender().getServiceID()).isNotNull();
    }

    @Test
    public void it_receives_a_valid_message() {
        try {
            engine.receive(message);
        } catch (Exception e) {
            fail("Receive raised an exception");
        }
    }


    @Test
    public void it_checks_the_messageId_exists() {
        Message message = newPush()
                .sender(newService().id("aSender"))
                .build();

        assertThatExceptionOfType(EmptyMessageIdEx.class)
                .isThrownBy(() -> engine.receive(message))
                .withMessageContaining("empty");
    }

    @Test
    public void it_receives_a_message_with_creation_datetime_equals_to_current_time_minus_3_hours() {
        when(config.isDateValidationEnabled()).thenReturn(true);

        Message message = newPush()
                .id("aMessageId")
                .sender(newService().id("aSender"))
                .creationDateTime(threeHoursInThePast())
                .build();

        assertThatExceptionOfType(CreationDateErrorEx.class)
                .isThrownBy(() -> engine.receive(message))
                .withMessageContaining("outside the allowed range");
    }

    @Test
    public void it_receives_a_message_with_creation_datetime_equals_to_current_time_plus_5_hours() {
        when(config.isDateValidationEnabled()).thenReturn(true);

        Message message = newPush()
                .id("aMessageId")
                .sender(newService().id("aSender"))
                .creationDateTime(fiveMinutesInTheFuture())
                .build();

        assertThatExceptionOfType(CreationDateErrorEx.class)
                .isThrownBy(() -> engine.receive(message))
                .withMessageContaining("outside the allowed range");
    }

    // TODO It should be understood on how to behave: should we be permissive or not on the service type
    // of the messages received?
    @Test
    @Ignore
    public void it_returns_a_SERVICE_TYPE_NOT_SUPPORTED_when_service_type_is_wrong() {
        when(config.serviceType()).thenReturn(ServiceType.EVENT_DOCUMENT_SERVICE);

        assertThat(engine.receive(message).getAckCode()).isEqualTo(SERVICE_TYPE_NOT_SUPPORTED);
    }

    // TODO It should be understood on how to behave: should we be permissive or not on the service type
    // of the messages received?
    @Test
    @Ignore
    public void it_returns_an_ack_error_description_when_service_type_is_wrong() {
        when(config.serviceType()).thenReturn(ServiceType.EVENT_DOCUMENT_SERVICE);

        assertThat(engine.receive(message).getAckDetail())
                .isEqualTo("Supported service type is VesselService");
    }

    @Test
    public void it_receives_a_valid_message_without_sender() {
        Push message = newPush().id("aMessageId").build();

        assertThatExceptionOfType(NullSenderEx.class)
                .isThrownBy(() -> engine.receive(message))
                .withMessageContaining("The sender of the message passed can't be null.");
    }

    @Test
    public void it_receives_a_valid_message_and_returns_the_acknowledge() {
        assertThat(engine.receive(message).getAckCode()).isEqualTo(SUCCESS);
    }

    // --------------- correlation id related test -------------//
    @Test
    public void it_receives_a_valid_message_and_returns_an_ack_with_correlation_id() {
        Acknowledgement ack = engine.receive(message);

        assertThat(ack.getCorrelationID()).isNotEmpty();
    }


    @Test
    public void it_receives_message_it_create_acknowledge_with_new_message_id() {
        Acknowledgement ack = engine.receive(message);
        String previousMessageId = message.getMessageID().toString();
        assertThat(ack.getMessageID()).isNotEqualTo(message.getMessageID().toString());
    }

    @Test
    public void it_receives_message_without_correlation_id_then_overwrite_it_with_previous_message_id() {

        message.setCorrelationID(null);
        String previousMessageId = message.getMessageID().toString();
        Acknowledgement ack = engine.receive(message);

        assertThat(ack.getCorrelationID()).isEqualTo(previousMessageId);
    }



    @Test
    public void it_answer_with_a_the_same_correlation_id_of_the_received_message() {
        message.setCorrelationID("aCorrelationId");
        Acknowledgement ack = engine.receive(message);

        assertThat(ack.getCorrelationID()).isEqualTo("aCorrelationId");
    }


    private Date threeHoursInThePast() {
        return Date.from(Instant.now().minus(3, HOURS));
    }

    private Date fiveMinutesInTheFuture() {
        return Date.from(Instant.now().plus(3, MINUTES));
    }
}