package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;

public interface MessageAPI {

    MessageApiDto send(String templateId, JsonNode json);

    Acknowledgement receive(String inputXmlMessage);

    MessageApiDto getLastStoredMessage();

    boolean consumeStoredMessage(MessageApiDto storedMessage);

}
