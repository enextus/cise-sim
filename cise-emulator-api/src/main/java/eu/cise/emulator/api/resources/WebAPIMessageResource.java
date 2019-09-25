package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.api.MessageAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/webapi/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WebAPIMessageResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAPIMessageResource.class);
    private final MessageAPI messageAPI;


    public WebAPIMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;

    }

    @POST
    public Response send(JsonNode msgWithParams) {
        LOGGER.info("messageCreate with param: {}", msgWithParams);

        JsonNode resultMessage = messageAPI.send(msgWithParams);
        Response.StatusType resultStatusType = (resultMessage.at("/status").textValue().contains("SUCCESS") ? Response.Status.CREATED : Response.Status.BAD_REQUEST);
        return Response
                .status(resultStatusType)
                .entity(resultMessage)
                .build();
    }

    @GET
    public Response receive() {
        LOGGER.info("messagePull from UI");
        JsonNode content = messageAPI.getLastStoredMessage();
        return Response
                .status(Response.Status.OK)
                .entity(content)
                .build();
    }

}
