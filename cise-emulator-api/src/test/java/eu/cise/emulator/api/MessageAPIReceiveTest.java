package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.TemplateLoader;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageAPIReceiveTest {

    private MessageProcessor messageProcessor;
    private MessageStorage messageStorage;
    private TemplateLoader templateLoader;
    private XmlMapper xmlMapper;

    @Before
    public void before() {
        xmlMapper = mock(XmlMapper.class);
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
        templateLoader = mock(TemplateLoader.class);
    }


    @Test
    public void it_calls_MessageStorage_to_obtain_last_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, templateLoader, xmlMapper);

        messageAPI.getLastStoredMessage();

        verify(messageStorage).read();
    }

    @Test
    public void it_returns_empty_when_NO_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, templateLoader, xmlMapper);
        when(messageStorage.read()).thenReturn(null);

        MessageApiDto response = messageAPI.getLastStoredMessage();

        assertThat(response).isNull();
    }

    @Test
    public void it_returns_last_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, templateLoader, xmlMapper);
        MessageApiDto mockedMessageApiDto = mock(MessageApiDto.class);

        when(messageStorage.read()).thenReturn(mockedMessageApiDto);

        MessageApiDto response = messageAPI.getLastStoredMessage();

        assertThat(response).isEqualTo(mockedMessageApiDto);
    }

}
