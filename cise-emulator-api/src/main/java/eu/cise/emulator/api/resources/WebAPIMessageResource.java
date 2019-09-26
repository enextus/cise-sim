package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
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
    private static final MessageApiDto DEFAULTRETURNPULL = new MessageApiDto(Response.Status.NO_CONTENT, "", "");
    private final MessageAPI messageAPI;


    public WebAPIMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;

    }

    @POST
    public Response send(JsonNode msgWithParams) {
        LOGGER.info("messageCreate with param: {}", msgWithParams);
        MessageApiDto resultMessage = messageAPI.send(msgWithParams);
        int resultStatusType = resultMessage.getStatus();
        return Response
                .status(resultStatusType)
                .entity(resultMessage)
                .build();
    }

    @GET
    public Response pull() {
        MessageApiDto resultMessage = DEFAULTRETURNPULL;

        if (messageAPI.getLastStoredMessage() != null) {
            LOGGER.debug("message Pulled from UI to " +
                    "Ressource with content : "
            );
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }

        return Response
                .status(resultMessage.getStatus())
                .entity(resultMessage)
                .build();
    }

}
