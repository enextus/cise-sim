package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.SendParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgWithParamMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgWithParamMapper.class);

    public SendParam extractSendParams(JsonNode json) {
        String messageId = json.at("/params/message-id").textValue();
        String correlationId = json.at("/params/correlation-id").textValue();
        boolean requiresAck = json.at("/params/requires-ack").booleanValue();
        return new SendParam(requiresAck, messageId, correlationId);
    }

    public String extractMessageTemplateHash(JsonNode msgWithParams) {
        return null;
    }
}


// params.put("requires-ack", "false");
//         params.put("message-id", "1234-123411-123411-1234");
//         params.put("correlation-id", "7777-666666-666666-5555");
//
//         msgTemplateWithParamObject.put("message-template", "hash-msg-template");
//         msgTemplateWithParamObject.set("params", params);