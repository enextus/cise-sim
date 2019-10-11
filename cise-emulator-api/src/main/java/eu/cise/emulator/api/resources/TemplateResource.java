package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.*;
import eu.cise.emulator.api.representation.TemplateParams;
import eu.cise.emulator.templates.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/ui/templates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TemplateResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateResource.class);
    private final MessageAPI messageAPI;
    private final TemplateAPI templateAPI;
    private final EmuConfig emuConfig;

    public TemplateResource(MessageAPI messageAPI, TemplateAPI templateAPI, EmuConfig emuConfig) {
        this.messageAPI = messageAPI;
        this.templateAPI = templateAPI;
        this.emuConfig = emuConfig;
    }

    @GET
    public Response getTemplates() {
        LOGGER.info("getTemplates");

        TemplateListResponse templateListResponse = templateAPI.getTemplates();

        List<Template> templateList = templateListResponse.getTemplates();

        if (!templateListResponse.isOk()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new APIError(templateListResponse.getError())).build();
        }

        return Response.status(Response.Status.OK)
                .entity(templateList)
                .build();
    }


    @GET
    @Path("{templateId}")
    public Response getTemplateById(
            @PathParam("templateId") String templateId,
            @QueryParam("messageId") String messageId,
            @QueryParam("correlationId") String correlationId,
            @QueryParam("requestAck") boolean requestAck) {

        PreviewResponse previewResponse = templateAPI.preview(
                new TemplateParams(templateId, messageId, correlationId, requestAck));

        if (!previewResponse.isOk()) {
            APIError apiError = new APIError(previewResponse.getErrorMessage());
            return Response.serverError().entity(apiError).build();
        }

        return Response.ok().entity(previewResponse.getTemplate()).build();
    }

    @POST
    @Path("{templateId}")
    public Response send(@PathParam("templateId") String templateId, JsonNode msgWithParams) {
        LOGGER.info("send called with param: {}", msgWithParams);
        MessageApiDto resultMessage = messageAPI.send(templateId, msgWithParams);
        if (resultMessage.getStatus() >= 300) {
            APIError apiError = new APIError(resultMessage.getErrorDetail());
            return Response.serverError().entity(apiError).build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity(resultMessage)
                .build();
    }

}
