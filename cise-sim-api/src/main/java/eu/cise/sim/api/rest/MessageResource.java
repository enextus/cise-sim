package eu.cise.sim.api.rest;

import eu.cise.sim.api.MessageAPI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/messages")
public class MessageResource {

    public static final String ERROR = "ERROR";
    private final MessageAPI messageAPI;


    public MessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;
    }

    @POST
    @Consumes({"application/xml", "text/plain", "text/xml"})
    @Produces("application/xml")
    public Response receive(String inputXmlMessage) {

        String acknowledgement = messageAPI.receiveXML(inputXmlMessage);

        return Response
                .status(Response.Status.CREATED)
                .entity(acknowledgement)
                .build();
    }
}
