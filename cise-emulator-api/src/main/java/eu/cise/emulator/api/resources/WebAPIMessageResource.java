package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import eu.cise.emulator.api.representation.SendingDataWrapper;
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

    @PATCH
    public Response send(JsonNode msgWithParams) {
        LOGGER.info("messageCreate with param: {}", msgWithParams);
        MessageApiDto resultMessage = messageAPI.send(msgWithParams);
        return Response
                .status(Response.Status.CREATED)
                .entity(resultMessage)
                .build();
    }

    @GET
    public Response pull() {
        LOGGER.info("messagePull from UI");
        MessageApiDto lastStoredMessage = messageAPI.getLastStoredMessage();
        return Response
                .status(Response.Status.OK)
                .entity(lastStoredMessage)
                .build();
    }

    @POST
    public Response preview(SendingDataWrapper dataWrapper) {
        LOGGER.info("Preview message with param: {}", dataWrapper);
        if (dataWrapper != null) {
            messageAPI.preview(dataWrapper.getParam(), dataWrapper.getTemplateHash());
        }
        return Response
                .status(Response.Status.OK)
                .build();
    }


}
