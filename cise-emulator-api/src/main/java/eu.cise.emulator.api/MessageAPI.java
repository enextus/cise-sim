package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.exceptions.EndpointNotFoundEx;

public interface MessageAPI {
    JsonNode send(JsonNode json) ;
}
