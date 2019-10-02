package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TemplateResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateResource.class);
    private final MessageAPI messageAPI;

    public TemplateResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;
    }

    @Path("/templates")
    @GET
    public Response getTemplates() {
        LOGGER.info("messagePull from UI");
        MessageApiDto lastStoredMessage = messageAPI.getLastStoredMessage();
        return Response
                .status(lastStoredMessage != null ? Response.Status.OK : Response.Status.NO_CONTENT)
                .entity(lastStoredMessage)
                .build();
    }

}
