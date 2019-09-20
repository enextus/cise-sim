package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.SendParam;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DefaultMessageAPI implements MessageAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);
    private MessageProcessor messageProcessor;
    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;

    public DefaultMessageAPI(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
        xmlMapper = new DefaultXmlMapper();
        LOGGER.debug(" Initialize the MessageAPI with default type implementation {} using message processor of type {}", this.getClass(), (messageProcessor != null ? messageProcessor.getClass() : ""));
    }

    @Override
    public JsonNode send(JsonNode json) {
        LOGGER.debug("send is passed through api : {}", json);
        String xmlContent = new SendSourceContentResolver().extractMessage(json);
        SendParam sendParam = new SendParamsReader().extractParams(json);
        Message message = xmlMapper.fromXML(xmlContent);
        MessageReturn jsonReturnBuilder = new MessageReturn("");
        JsonNode jsonReturn = null;
        try {
            Acknowledgement acknowledgement = messageProcessor.send(message, sendParam);
            jsonReturn = jsonReturnBuilder.build("SUCCESS", xmlMapper.toXML(acknowledgement), "");
        } catch (Exception e) {
            jsonReturn = jsonReturnBuilder.build("ERROR: " + e.getClass() + " : " + e.getMessage(), "", "");
        }
        return jsonReturn;
    }

    private class MessageReturn {
        final String source;
        final ObjectMapper jsonmapper = new ObjectMapper();
        final ObjectNode innerRootJsonNode = jsonmapper.createObjectNode();
        boolean errorFlag = true;

        private MessageReturn(String source) {
            this.source = source;
        }


        public boolean isError() {
            return errorFlag;
        }

        public JsonNode build(String refError, String refAcknowledge, String refMessageString) {
            if (refError.isEmpty()) this.errorFlag = false;
            innerRootJsonNode.put("status", refError);
            innerRootJsonNode.put("body", refMessageString);
            innerRootJsonNode.put("ack", refAcknowledge);
            JsonNode suportedNode = null;
            try {
                suportedNode = (JsonNode) jsonmapper.readTree(innerRootJsonNode.toString());
            } catch (IOException e) {

            }
            return (suportedNode);
        }
    }
}

