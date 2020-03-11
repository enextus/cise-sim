package eu.cise.cli;

import eu.cise.emulator.EmulatorEngine;
import eu.cise.emulator.SendParam;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UseCaseSendMessageTest {
    private UseCaseSendMessage useCaseSendMessage;
    private EmulatorEngine emulatorEngine;
    private MessageLoader loader;
    private Push messageLoaded;
    private Acknowledgement returnedAck;
    private Message preparedMessage;

    @Before
    public void before() {
        emulatorEngine = mock(EmulatorEngine.class);
        loader = mock(MessageLoader.class);

        useCaseSendMessage = new UseCaseSendMessage(emulatorEngine, loader);

        messageLoaded = new Push();
        preparedMessage = new Push();
        returnedAck = new Acknowledgement();

        when(loader.load(anyString())).thenReturn(messageLoaded);
        when(emulatorEngine.prepare(any(),any())).thenReturn(preparedMessage);
        when(emulatorEngine.send(any())).thenReturn(returnedAck);
    }

    @After
    public void after() {
        reset(loader);
        reset(emulatorEngine);
    }

    @Test
    public void it_loads_a_message_from_disk() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(emulatorEngine).prepare(messageLoaded, sendParam);
    }

    @Test
    public void it_sends_a_prepared_message() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(emulatorEngine).send(preparedMessage);
    }

    @Test
    public void it_saves_the_prepared_message() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(loader).saveSentMessage(preparedMessage);
    }

    @Test
    public void it_saves_the_returned_ack_message() {
        SendParam sendParam = new SendParam(true, "123", "");

        useCaseSendMessage.send("filename.xml", sendParam);

        verify(loader).saveReturnedAck(returnedAck);
    }
}
