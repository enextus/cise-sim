package eu.cise.emulator;


import static eu.eucise.helpers.PushBuilder.newPush;
import static org.mockito.Mockito.*;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.emulator.utils.FakeSignatureService;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
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
    }

    @Test
    public void it_send_the_message() {
        Message message =  newPush().build();

        when(config.endpointUrl()).thenReturn("endpointUrl");

        engine.send(message);

        verify(dispatcher).send(message, "endpointUrl");
    }
}