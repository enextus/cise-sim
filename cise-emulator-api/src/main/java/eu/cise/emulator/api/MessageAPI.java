package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.SendParam;
import eu.cise.servicemodel.v1.message.Acknowledgement;

public interface MessageAPI {
    MessageApiDto send(String templateId, JsonNode json);

    Acknowledgement receive(String inputXmlMessage);

    MessageApiDto getLastStoredMessage();

    MessageApiDto preview(SendParam jsonNode, String templateHash);

    boolean consumeStoredMessage(MessageApiDto storedMessage);

}
