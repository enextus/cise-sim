package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public interface MessageAPI {

    SendResponse send(String templateId, JsonNode json);
    SendResponse send(Message message);

    String receiveXML(String inputXmlMessage);
    Acknowledgement receive(Message message);

}
