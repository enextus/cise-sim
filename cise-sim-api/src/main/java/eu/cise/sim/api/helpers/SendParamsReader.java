package eu.cise.sim.api.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.SendParam;

public class SendParamsReader {

    public SendParam extractParams(JsonNode json) {
        String messageId = json.at("/messageId").textValue();
        String correlationId = json.at("/correlationId").textValue();
        boolean requiresAck = json.at("/requiresAck").booleanValue();
        return new SendParam(requiresAck, messageId, correlationId);
    }
}
