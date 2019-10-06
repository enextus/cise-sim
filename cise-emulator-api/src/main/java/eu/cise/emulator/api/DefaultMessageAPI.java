package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.helpers.SendParamsReader;
import eu.cise.emulator.api.resources.WebAPIMessageResource;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

import static eu.eucise.helpers.PushBuilder.newPush;

public class DefaultMessageAPI implements MessageAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAPIMessageResource.class);
    private final MessageStorage messageStorage;
    private final MessageProcessor messageProcessor;
    private final XmlMapper xmlMapper;
    private final TemplateLoader templateLoader;

    DefaultMessageAPI(MessageProcessor messageProcessor,
                      MessageStorage messageStorage,
                      TemplateLoader templateLoader) {

        this.messageProcessor = messageProcessor;
        this.messageStorage = messageStorage;
        this.xmlMapper = new DefaultXmlMapper();
        this.templateLoader = templateLoader;

        LOGGER.debug(" Initialize the MessageAPI with default type implementation {} using message processor of type {}", this.getClass(), (messageProcessor != null ? messageProcessor.getClass() : ""));
    }

    @Override
    public MessageApiDto send(JsonNode json) {
        LOGGER.debug("send is passed through api : {}", json);
        String templateId = json.at("/message_template").textValue();
        Template template = templateLoader.loadTemplate(templateId);
        String xmlContent = template.getTemplateContent();
        SendParam sendParam = new SendParamsReader().extractParams(json);
        Message message = xmlMapper.fromXML(xmlContent);

        try {
            Acknowledgement acknowledgement = messageProcessor.send(message, sendParam);
            return new MessageApiDto(Response.Status.ACCEPTED.getStatusCode(), null, xmlMapper.toXML(acknowledgement), "");
        } catch (Exception e) {
            LOGGER.error("error in Api send", e);
            return new MessageApiDto(Response.Status.BAD_REQUEST.getStatusCode(), "Error in Api send", "", "");
        }
    }

    @Override
    public Acknowledgement receive(String content) {
        LOGGER.debug("receive is receiving through api : {}", content.substring(0, 200));
        CiseMessageResponse ciseMessageResponse;
        Acknowledgement acknowledgement = null;
        try {
            Message message = xmlMapper.fromXML(content);
            acknowledgement = messageProcessor.receive(message);
            ciseMessageResponse = new CiseMessageResponse(xmlMapper, acknowledgement, message);
        } catch (Exception e) {
            LOGGER.error("error in Api send {}", e);
            ciseMessageResponse = new CiseMessageResponse(content);
        }
        return acknowledgement;
    }

    @Override
    public MessageApiDto getLastStoredMessage() {
        MessageApiDto returnApiDto = null;
        if (messageStorage != null) {
            returnApiDto = (MessageApiDto) messageStorage.read();
        }
        return returnApiDto;
    }

    @Override
    public MessageApiDto preview(SendParam param, String templateHash) {
        //TODO: read the template from fileStorage
        Message message = newPush().build();
        Message preview = messageProcessor.preview(message, param);
        return new MessageApiDto(Response.Status.OK.getStatusCode(), "", "", xmlMapper.toXML(preview));
    }


}
