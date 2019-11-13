package eu.cise.emulator.send;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;

public interface MessageAPI {

    SendResponse send(String templateId, JsonNode json);

    Acknowledgement receive(String inputXmlMessage);

    MessageApiDto getLastStoredMessage();

    boolean consumeStoredMessage(MessageApiDto storedMessage);

}
