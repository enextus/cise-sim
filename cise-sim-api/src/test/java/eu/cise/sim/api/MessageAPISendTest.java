package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.cise.sim.utils.Pair;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageAPISendTest {

    private MessageProcessor messageProcessor;
    private MessageStorage messageStorage;
    private MessageStorage historyMessageStorage;
    private TemplateLoader templateLoader;
    private Push pushMessage;
    private Acknowledgement ackMessage;
    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;
    private XmlMapper concreteNotValidatingXmlMapper;

    @Before
    public void before() {
        xmlMapper = mock(XmlMapper.class);
        concreteNotValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        jsonMapper = new ObjectMapper();
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
        historyMessageStorage = mock(MessageStorage.class);
        templateLoader = mock(TemplateLoader.class);
        pushMessage = newPush().build();
        ackMessage = newAck().build();
    }

    @Test
    public void it_returns_a_messageApiDto_with_the_acknowledge_received_on_successful_send() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, historyMessageStorage, templateLoader, xmlMapper, concreteNotValidatingXmlMapper);

        Template loadedTemplate = mock(Template.class);
        when(templateLoader.loadTemplate(any())).thenReturn(loadedTemplate);
        when(messageProcessor.send(any(), any())).thenReturn(new Pair(ackMessage, pushMessage));
        when(xmlMapper.fromXML(any())).thenReturn(pushMessage);
        String ackAsString = concreteNotValidatingXmlMapper.toXML(ackMessage);
        when(xmlMapper.toXML(any())).thenReturn(ackAsString);

        SendResponse sendResponse = messageAPI.send("template-id", msgParams());

        assertThat(sendResponse.getContents().getAcknowledge()).isEqualTo(ackAsString);
    }

    @Ignore
    @Test
    public void it_returns_a_messageApiDto_with_the_message_sent_on_successful_send() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, historyMessageStorage, templateLoader, xmlMapper, concreteNotValidatingXmlMapper);

        Template loadedTemplate = mock(Template.class);
        when(templateLoader.loadTemplate(any())).thenReturn(loadedTemplate);
        when(messageProcessor.send(any(), any())).thenReturn(new Pair(ackMessage, pushMessage));
        when(xmlMapper.fromXML(any())).thenReturn(pushMessage);
        String messageAsString = concreteNotValidatingXmlMapper.toXML(ackMessage);
        when(xmlMapper.toXML(any())).thenReturn(messageAsString);

        SendResponse sendResponse = messageAPI.send("template-id", msgParams());

        assertThat(sendResponse.getContents().getBody()).isEqualTo(messageAsString);
    }

    private JsonNode msgParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        msgTemplateWithParamObject.put("requiresAck", true);
        msgTemplateWithParamObject.put("messageId", "1234-123411-123411-1234");
        msgTemplateWithParamObject.put("correlationId", "7777-666666-666666-5555");

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}
