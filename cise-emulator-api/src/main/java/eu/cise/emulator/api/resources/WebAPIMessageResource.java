package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/ui/messages/latest")
@Produces(MediaType.APPLICATION_JSON)
public class WebAPIMessageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAPIMessageResource.class);
    private final MessageAPI messageAPI;


    public WebAPIMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;

    }

    @DELETE
    public Response pullAndDelete() {
        LOGGER.info("messagePull from UI");

        MessageApiDto lastStoredMessage = messageAPI.getLastStoredMessage();

        if (lastStoredMessage == null) {
            return Response
                .status(Response.Status.NO_CONTENT)
                .build();
        }
        boolean isConsumed = messageAPI.consumeStoredMessage(lastStoredMessage);
        LOGGER.info("lastStoredMessage was consumed : " + isConsumed + " with content : "
            + lastStoredMessage.toString());

        return Response
            .status(Response.Status.OK)
            .entity(lastStoredMessage)
            .build();

    }

}
