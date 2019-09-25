package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;

public interface MessageAPI {
    JsonNode send(JsonNode json);

    CiseMessageResponse receive(String inputXmlMessage) throws Exception;

    void getLastStoredMessage();
}
