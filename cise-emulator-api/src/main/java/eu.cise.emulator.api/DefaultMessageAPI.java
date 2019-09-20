package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.exceptions.EndpointNotFoundEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public JsonNode send(JsonNode json)  {
        LOGGER.debug("send is passed through api : {}", json);
        String xmlContent = new SendSourceContentResolver().extractMessage(json);
        SendParam sendParam = new SendParamsReader().extractParams(json);
        Message message = xmlMapper.fromXML(xmlContent);
        MessageReturn messageReturn=new MessageReturn("");
        try {
            Acknowledgement acknowledgement = messageProcessor.send(message, sendParam);
            messageReturn.build( "SUCCESS", xmlMapper.toXML(acknowledgement), "");
        } catch (Exception e) {
            messageReturn.build( "ERROR: "+e.getClass() +" : " + e.getMessage(), "", "");
        }

        return jsonMapper.valueToTree(messageReturn);
    }

    private class MessageReturn {
        final String source;
        final ObjectMapper jsonmapper = new ObjectMapper();
        final ArrayNode jsonmapperarrayNode = jsonmapper.createArrayNode();
        boolean errorFlag = true;

        private MessageReturn(String source) {
            this.source = source;
        }


        public boolean isError(){
            return errorFlag;
        }
        public ArrayNode build(String refError, String refAcknowledge, String refMessageString) {
            if (refError.isEmpty()) this.errorFlag = false;
            ObjectNode objectNode1 = jsonmapper.createObjectNode();
            objectNode1.put("status", refError);
            objectNode1.put("body", refMessageString);
            objectNode1.put("ack", refAcknowledge);
            jsonmapperarrayNode.add(objectNode1);
            return (jsonmapperarrayNode);
        }
    }
}

