package eu.cise.emulator.api.resources;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.APIError;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.PreviewResponse;
import eu.cise.emulator.api.TemplateAPI;
import eu.cise.emulator.api.helpers.TemplatesResolver;
import eu.cise.emulator.api.representation.TemplateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/templates")
@Produces(MediaType.APPLICATION_JSON)
public class TemplateResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateResource.class);
    private final MessageAPI messageAPI;
    private final TemplateAPI templateAPI;
    private final EmuConfig emuConfig;
    private final TemplatesResolver templatesResolver;

    public TemplateResource(MessageAPI messageAPI, TemplateAPI templateAPI, EmuConfig emuConfig) {
        this.messageAPI = messageAPI;
        this.templateAPI = templateAPI;
        this.emuConfig = emuConfig;
        templatesResolver = new TemplatesResolver(emuConfig);
    }

    @GET
    public Response getTemplates() {
        LOGGER.info("getTemplates");
        
        List<String> filesList = templatesResolver.listMessages();

        return Response
                .status(filesList.size() > 0 ? Response.Status.OK : Response.Status.NO_CONTENT)
                .entity(filesList)
                .build();
    }


    @GET
    @Path("{templateId}")
    public Response getTemplateById(
            @PathParam("templateId") String templateId,
            @QueryParam("messageId") String messageId,
            @QueryParam("correlationId") String correlationId,
            @QueryParam("requestAck") boolean requestAck) {
        PreviewResponse previewResponse = templateAPI.preview(new TemplateParams(templateId, messageId, correlationId, requestAck));

        if (!previewResponse.isOk()) {
            APIError apiError = new APIError(previewResponse.getErrorMessage());
            return Response.serverError().entity(apiError).build();
        }

        return Response.ok().entity(previewResponse.getTemplate()).build();
    }

}
