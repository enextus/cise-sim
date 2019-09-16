package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.SendParam;

public class MsgWithParamMapper {

    public SendParam extractSendParams(JsonNode json) {
        String messageId = json.at("/params/message-id").textValue();
        String correlationId = json.at("/params/correlation-id").textValue();
        boolean requiresAck = json.at("/params/requires-ack").booleanValue();
        return new SendParam(requiresAck, messageId, correlationId);
    }

    public String extractMessageTemplateHash(JsonNode json) {
        return json.at("/message-template").textValue();
    }
}
