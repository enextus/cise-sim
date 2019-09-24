package eu.cise.emulator.api.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.SendParam;

public class SendParamsReader {

    public SendParam extractParams(JsonNode json) {
        String messageId = json.at("/params/message_id").textValue();
        String correlationId = json.at("/params/correlation_id").textValue();
        boolean requiresAck = json.at("/params/requires_ack").booleanValue();
        return new SendParam(requiresAck, messageId, correlationId);
    }
}
