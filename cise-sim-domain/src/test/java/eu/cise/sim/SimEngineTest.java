package eu.cise.sim;


import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.DefaultSimEngine;
import eu.cise.sim.engine.DispatchResult;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.SimEngine;
import eu.cise.sim.exceptions.*;
import eu.cise.sim.utils.Scenarios;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static eu.cise.servicemodel.v1.message.AcknowledgementType.SUCCESS;
import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimEngineTest {

    private SimConfig config;
    private SimEngine engine;
    private Dispatcher dispatcher;
    private Push message;

    @Before
    public void before() {
        config = mock(SimConfig.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultSimEngine(mock(SignatureService.class), dispatcher, config);
        message = newPush()
                .id("aMessageId")
                .sender(newService().id("aServiceId").type(VESSEL_SERVICE))
                .recipient(newService().id("recipient-id"))
                .build();

        when(config.destinationUrl()).thenReturn("endpointUrl");
    }

    @After
    public void after() {
        reset(config);
    }

    @Test
    public void it_sends_message_successfully() {
        when(dispatcher.send(message, config.destinationUrl())).thenReturn(
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
        when(dispatcher.send(message, config.destinationUrl())).thenReturn(
                new DispatchResult(true, Scenarios.getSyncAckMsgSuccess()));

        assertThat(engine.send(message).getAckCode()).isEqualTo(SUCCESS);
    }

    @Test
    public void it_sends_a_message_getting_an_unsuccessful_response() {
        when(dispatcher.send(message, config.destinationUrl())).thenReturn(
                new DispatchResult(false, null));

        assertThatExceptionOfType(EndpointErrorEx.class)
                .isThrownBy(() -> engine.send(message))
                .withMessageContaining("endpoint returned an error");
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
    @Ignore
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
        String previousMessageId = message.getMessageID();
        assertThat(ack.getMessageID()).isNotEqualTo(message.getMessageID());
    }

    @Test
    public void it_receives_message_without_correlation_id_then_overwrite_it_with_previous_message_id() {

        message.setCorrelationID(null);
        String previousMessageId = message.getMessageID();
        Acknowledgement ack = engine.receive(message);

        assertThat(ack.getCorrelationID()).isEqualTo(previousMessageId);
    }


    @Test
    public void it_answer_with_a_the_same_correlation_id_of_the_received_message() {
        message.setCorrelationID("aCorrelationId");
        Acknowledgement ack = engine.receive(message);

        assertThat(ack.getCorrelationID()).isEqualTo("aCorrelationId");
    }


}