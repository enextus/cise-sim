package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.servicemodel.v1.message.Acknowledgement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/messages")
public class MessageResource {

    public static final String ERROR = "ERROR";
    private final MessageAPI messageAPI;
    private final MessageStorage messageStorage;


    public MessageResource(MessageAPI messageAPI, MessageStorage messageStorage) {
        this.messageAPI = messageAPI;
        this.messageStorage = messageStorage;
    }

    @POST
    @Consumes({"application/xml", "text/plain", "text/xml"})
    @Produces("application/xml")
    public Response receive(String inputXmlMessage) {

        Acknowledgement acknowledgement = messageAPI.receive(inputXmlMessage);
        return Response
                .status(Response.Status.CREATED)
                .entity(acknowledgement)
                .build();
    }
}
