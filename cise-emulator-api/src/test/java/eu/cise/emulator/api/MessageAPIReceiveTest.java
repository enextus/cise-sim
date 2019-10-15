package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.resources.MessageBuilderUtil;
import eu.cise.emulator.io.DefaultMessageStorage;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
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


    // -------  concerning the CISE api :  node to/from lsa  ---------//
    @Test
    public void it_stores_something_when_invoked_receive() {
        Acknowledgement acknowledgement = MessageBuilderUtil.createAcknowledgeMessage();
        when(messageProcessor.receive(any())).thenReturn(acknowledgement);
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, templateLoader, xmlMapper);
        Acknowledgement response = messageAPI.receive(MessageBuilderUtil.TEST_MESSAGE_XML);
        verify(messageStorage).store(any());
    }

    @Test
    public void it_stores_consistent_value_of_acknowledge_when_invoked_receive() {
        MessageStorage realMessageStorage = new DefaultMessageStorage();
        XmlMapper xmlMapper = new DefaultXmlMapper.PrettyNotValidating();

        Acknowledgement acknowledgement = MessageBuilderUtil.createAcknowledgeMessage();
        when(messageProcessor.receive(any())).thenReturn(acknowledgement);

        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, realMessageStorage, templateLoader, xmlMapper);
        Acknowledgement response = messageAPI.receive(MessageBuilderUtil.TEST_MESSAGE_XML);
        Acknowledgement awaitedResponse = xmlMapper.fromXML(((MessageApiDto) realMessageStorage.read()).getAcknowledge());
        assertThat(response).isEqualTo(awaitedResponse);
    }

    @Test
    public void it_stores_consistent_value_of_message_when_invoked_receive() {
        MessageStorage realMessageStorage = new DefaultMessageStorage();
        XmlMapper xmlMapper = new DefaultXmlMapper.PrettyNotValidating();

        Acknowledgement acknowledgement = MessageBuilderUtil.createAcknowledgeMessage();
        when(messageProcessor.receive(any())).thenReturn(acknowledgement);

        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, realMessageStorage, templateLoader, xmlMapper);

        Acknowledgement response = messageAPI.receive(MessageBuilderUtil.TEST_MESSAGE_XML);
        Message sentMessage = xmlMapper.fromXML(MessageBuilderUtil.TEST_MESSAGE_XML);
        Message storedResponse = xmlMapper.fromXML(((MessageApiDto) realMessageStorage.read()).getBody());
        assertThat(sentMessage).isEqualTo(storedResponse);
    }

}
