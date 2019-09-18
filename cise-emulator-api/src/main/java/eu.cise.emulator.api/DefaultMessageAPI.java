package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMessageAPI implements MessageAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);
    private MessageProcessor messageProcessor;
    private ObjectMapper jsonMapper;

    public DefaultMessageAPI(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
        LOGGER.debug(" Initialize the MessageAPI with default type implementation {} using message processor of type {}", this.getClass(), (messageProcessor != null ? messageProcessor.getClass() : ""));
    }

    @Override
    public JsonNode send(JsonNode json) {
        LOGGER.debug("send is passed through api : {}", json);
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }
}

