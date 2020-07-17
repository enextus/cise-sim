package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public interface MessageAPI {

    ResponseApi<MessageResponse>  send(String templateId, JsonNode json);
    ResponseApi<MessageResponse>  send(Message message);

    ResponseApi<String> receiveXML(String inputXmlMessage);
    ResponseApi<Acknowledgement> receive(Message message);

}
