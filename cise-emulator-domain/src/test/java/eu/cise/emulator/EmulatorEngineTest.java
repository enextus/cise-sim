package eu.cise.emulator;


import static eu.cise.servicemodel.v1.message.AcknowledgementType.END_POINT_NOT_FOUND;
import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.utils.FakeSignatureService;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmulatorEngineTest {

    private SignatureService signatureService;
    private EmuConfig config;
    private MessageProcessor messageProcessor;
    private EmulatorEngine engine;
    private Dispatcher dispatcher;

    @Before
    public void before() {
        signatureService = new FakeSignatureService();
        config = mock(EmuConfig.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultEmulatorEngine(signatureService, dispatcher, config);
        when(config.endpointUrl()).thenReturn("endpointUrl");
    }

    @After
    public void after() {
        reset(config);
    }

    @Test
    public void it_sends_message_successfully() {
        Message message =  newPush().build();

        engine.send(message);

        verify(dispatcher).send(message, "endpointUrl");
    }

    @Test
    public void it_sends_message_failing_the_dispatch() {
        Message message =  newPush().build();

        doThrow(dispatcherException()).when(dispatcher).send(any(), any());

        Acknowledgement ack = engine.send(message);

        assertThat(ack.getAckCode()).isEqualTo(END_POINT_NOT_FOUND);
    }

    private DispatcherException dispatcherException() {
        return new DispatcherException("Error while connecting to address|" + config.endpointUrl(), null);
    }

}