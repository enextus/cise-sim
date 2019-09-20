package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Entity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DefaultMessageResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);
    private final MessageAPI messageAPI;


    public DefaultMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;

    }

    @POST
    @Path("/messages")
    public Response send(JsonNode msgWithParams)  {
        LOGGER.info("messageCreate with param: {}", msgWithParams);

        JsonNode messageReturn = messageAPI.send(msgWithParams);

        return Response
                .status(Response.Status.CREATED)
                .entity(messageReturn)
                .build();
    }

}
