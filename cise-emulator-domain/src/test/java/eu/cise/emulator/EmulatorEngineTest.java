package eu.cise.emulator;


import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.exceptions.EndpointNotFoundEx;
import eu.cise.emulator.utils.FakeSignatureService;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.signature.SignatureService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class EmulatorEngineTest {

    private static final String ENDPOINT_URL = "endpointUrl";

    private SignatureService signatureService;
    private EmuConfig config;
    private MessageProcessor messageProcessor;
    private EmulatorEngine engine;
    private Dispatcher dispatcher;
    private Push message;

    @Before
    public void before() {
        signatureService = new FakeSignatureService();
        config = mock(EmuConfig.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultEmulatorEngine(signatureService, dispatcher, config);
        message = newPush().build();

        when(config.serviceId()).thenReturn("service-id");
        when(config.endpointUrl()).thenReturn(ENDPOINT_URL);
    }

    @After
    public void after() {
        reset(config);
    }

    @Test
    public void it_sends_message_successfully() {
        try {
            engine.send(message);
        } catch (EndpointNotFoundEx endpointNotFoundEx) {
            // do nothing
        }

        verify(dispatcher).send(message, ENDPOINT_URL);
    }

    @Test
    public void it_sends_a_message_failing_the_dispatch_for_end_point_not_found() {
        when(dispatcher.send(message, ENDPOINT_URL)).thenThrow(DispatcherException.class);

        assertThatExceptionOfType(EndpointNotFoundEx.class)
                .isThrownBy(() -> engine.send(message))
                .withMessageContaining("endpoint not found");
    }

//    @Ignore
//    @Test
//    public void it_returns_a_sync_ack_with_an_ack_code_successful() {
//        when(dispatcher.send(message, config.endpointUrl())).thenReturn(DispatchResult.success());
//
//        Acknowledgement ack = null;
//        try {
//            ack = engine.send(message);
//        } catch (EndpointNotFoundEx endpointNotFoundEx) {
//            // do nothing
//        }
//
//        assertThat(ack.getAckCode()).isEqualTo(SUCCESS);
//    }
//
//    @Ignore
//    @Test
//    public void it_returns_a_sync_ack_with_a_sender_not_null() {
//        when(dispatcher.send(message, config.endpointUrl())).thenReturn(DispatchResult.success());
//
//        Acknowledgement ack = engine.send(message);
//
//        assertThat(ack.getSender()).isNotNull();
//    }
//
//    @Ignore
//    @Test
//    public void it_returns_a_sync_ack_with_a_sender_serviceId() {
//        when(dispatcher.send(message, config.endpointUrl())).thenReturn(DispatchResult.success());
//
//        Acknowledgement ack = engine.send(message);
//
//        assertThat(ack.getSender().getServiceID()).isEqualTo("service-id");
//    }

    private DispatcherException dispatcherException() {
        return new DispatcherException("Error while connecting to address|" + config.endpointUrl(), null);
    }

}