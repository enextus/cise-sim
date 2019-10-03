package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.io.MessageStorage;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class APIMessageTest {

    public static MessageProcessor messageProcessor;
    public static MessageStorage messageStorage;

    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        messageProcessor = mock (MessageProcessor.class);
        messageStorage = mock (MessageStorage.class);
        jsonMapper = new ObjectMapper();
    }


    @Test
    public void it_calls_MessageStorage_to_obtain_last_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage);
        messageAPI.getLastStoredMessage();
        verify(messageStorage).read();
    }

    @Test
    public void it_returns_empty_when_NO_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor , messageStorage);
        when(messageStorage.read()).thenReturn(null);

        MessageApiDto response = (MessageApiDto) messageAPI.getLastStoredMessage();

        assertThat(response).isNull();
    }

    @Test
    public void it_returns_last_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor , messageStorage);
        MessageApiDto mockedMessageApiDto = mock(MessageApiDto.class);
        when(messageStorage.read()).thenReturn(mockedMessageApiDto);

        MessageApiDto response = (MessageApiDto) messageAPI.getLastStoredMessage();

        assertThat(response).isEqualTo(mockedMessageApiDto);
    }

}
