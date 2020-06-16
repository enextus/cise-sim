package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.dto.MessageApiDto;

public interface MessageAPI {

    SendResponse send(String templateId, JsonNode json);
    SendResponse send(Message message);

    Acknowledgement receive(String inputXmlMessage);

    MessageApiDto getLastStoredMessage();

    boolean consumeStoredMessage(MessageApiDto storedMessage);

}
