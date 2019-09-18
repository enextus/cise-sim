package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DefaultMessageResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);
    private final MessageAPI messageAPI;


    DefaultMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;

    }

    @POST
    @Path("/messages")
    public Response send(JsonNode msgWithParams) {
        LOGGER.info("messageCreate with param: {}", msgWithParams);

        JsonNode responseObject= messageAPI.send(msgWithParams);
        return Response.status(Response.Status.CREATED).build();

    }


}
