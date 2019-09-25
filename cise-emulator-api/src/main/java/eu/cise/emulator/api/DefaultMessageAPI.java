package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.helpers.SendParamsReader;
import eu.cise.emulator.api.helpers.SendSourceContentResolver;
import eu.cise.emulator.api.resources.WebAPIMessageResource;
import eu.cise.io.MessageStorage;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class DefaultMessageAPI implements MessageAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAPIMessageResource.class);
    private MessageProcessor messageProcessor;
    private final MessageStorage messageStorage;
    private XmlMapper xmlMapper;

    public DefaultMessageAPI(MessageProcessor messageProcessor, MessageStorage messageStorage) {
        this.messageProcessor = messageProcessor;
        this.messageStorage = messageStorage;
        xmlMapper = new DefaultXmlMapper();
        LOGGER.debug(" Initialize the MessageAPI with default type implementation {} using message processor of type {}", this.getClass(), (messageProcessor != null ? messageProcessor.getClass() : ""));
    }

    @Override
    public MessageApiDto send(JsonNode json) {
        LOGGER.debug("send is passed through api : {}", json);
        String xmlContent = new SendSourceContentResolver().extractMessage(json);
        SendParam sendParam = new SendParamsReader().extractParams(json);
        Message message = xmlMapper.fromXML(xmlContent);
        MessageApiDto returnedApiDto = null;
        try {
            Acknowledgement acknowledgement = messageProcessor.send(message, sendParam);
            returnedApiDto = new MessageApiDto(Response.Status.ACCEPTED, xmlMapper.toXML(acknowledgement), "");
        } catch (Exception e) {
            //TODO: interpret the exception to show the right error to the UI
            LOGGER.error("error in Api send {}", e);
            returnedApiDto = new MessageApiDto(Response.Status.BAD_REQUEST, "", "");
        }
        return returnedApiDto;
    }

    @Override
    public CiseMessageResponse receive(String content) {
        LOGGER.debug("receive is receiving through api : {}", content.substring(0, 200));
        CiseMessageResponse ciseMessageResponse;
        try {
            Message message = xmlMapper.fromXML(content);
            Acknowledgement acknowledgement = messageProcessor.receive(message);
            //TODO: call to message processor returning acknowledgement
            ciseMessageResponse = new CiseMessageResponse(xmlMapper, acknowledgement, message);
        } catch (Exception e) {
            LOGGER.error("error in Api send {}", e);
            ciseMessageResponse = new CiseMessageResponse(content);
        }
        return ciseMessageResponse;
    }

    @Override
    public MessageApiDto getLastStoredMessage() {
        return (MessageApiDto) messageStorage.read();
    }


}
