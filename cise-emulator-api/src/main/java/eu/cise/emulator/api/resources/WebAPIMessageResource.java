package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @GET
    public Response pull() {
        LOGGER.info("messagePull from UI");
        MessageApiDto lastStoredMessage = messageAPI.getLastStoredMessage();

        if (lastStoredMessage != null)
            LOGGER.info("lastStoredMessage: " + lastStoredMessage.toString());

        return Response
                .status(lastStoredMessage != null ? Response.Status.OK : Response.Status.NO_CONTENT)
                .entity(lastStoredMessage)
                .build();
    }
}
