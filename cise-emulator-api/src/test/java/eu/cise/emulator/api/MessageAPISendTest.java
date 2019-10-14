package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Push;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageAPISendTest {

    private MessageProcessor messageProcessor;
    private MessageStorage messageStorage;
    private TemplateLoader templateLoader;
    private Push pushMessage;
    private Acknowledgement ackMessage;
    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;
    private XmlMapper concreteXmlMapper;

    @Before
    public void before() {
        xmlMapper = mock(XmlMapper.class);
        concreteXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        jsonMapper = new ObjectMapper();
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
        templateLoader = mock(TemplateLoader.class);
        pushMessage = newPush().build();
        ackMessage = newAck().build();
    }

    @Test
    public void it_returns_a_messageApiDto_with_the_acknowledge_received_on_successful_send() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, templateLoader, xmlMapper);

        Template loadedTemplate = mock(Template.class);
        when(templateLoader.loadTemplate(any())).thenReturn(loadedTemplate);
        when(messageProcessor.send(any(), any())).thenReturn(ackMessage);
        when(xmlMapper.fromXML(any())).thenReturn(pushMessage);
        String ackAsString = concreteXmlMapper.toXML(ackMessage);
        when(xmlMapper.toXML(any())).thenReturn(ackAsString);

        MessageApiDto sendResponse = messageAPI.send("template-id", msgParams());

        assertThat(sendResponse.getAcknowledge()).isEqualTo(ackAsString);
    }

    @Test
    public void it_returns_a_messageApiDto_with_the_message_sent_on_successful_send() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, templateLoader, xmlMapper);

        Template loadedTemplate = mock(Template.class);
        when(templateLoader.loadTemplate(any())).thenReturn(loadedTemplate);
        when(messageProcessor.send(any(), any())).thenReturn(ackMessage);
        when(xmlMapper.fromXML(any())).thenReturn(pushMessage);
        String messageAsString = concreteXmlMapper.toXML(ackMessage);
        when(xmlMapper.toXML(any())).thenReturn(messageAsString);

        MessageApiDto sendResponse = messageAPI.send("template-id", msgParams());

        assertThat(sendResponse.getBody()).isEqualTo(messageAsString);
    }

    private JsonNode msgParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        msgTemplateWithParamObject.put("requiresAck", true);
        msgTemplateWithParamObject.put("messageId", "1234-123411-123411-1234");
        msgTemplateWithParamObject.put("correlationId", "7777-666666-666666-5555");

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}
