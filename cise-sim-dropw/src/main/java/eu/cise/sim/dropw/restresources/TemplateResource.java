package eu.cise.sim.dropw.restresources;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.MessageResponse;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.TemplateAPI;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.dropw.helpers.BuildHelper;
import eu.cise.sim.templates.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/ui/templates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TemplateResource {

    private final Logger logger;

    private final MessageAPI  messageAPI;
    private final TemplateAPI templateAPI;

    public TemplateResource(MessageAPI messageAPI, TemplateAPI templateAPI) {
        this.messageAPI = messageAPI;
        this.templateAPI = templateAPI;
        this.logger = LoggerFactory.getLogger(TemplateResource.class);
    }

    @GET
    public Response getTemplates() {
        logger.debug("requesting messages templates to the server");
        ResponseApi<List<Template>> templateListResponse = templateAPI.getTemplates();
        return BuildHelper.buildResponse(templateListResponse, Response.Status.INTERNAL_SERVER_ERROR);
    }


    @GET
    @Path("{templateId}")
    public Response getTemplateById(
        @PathParam("templateId")     String templateId,
        @QueryParam("messageId")     String messageId,
        @QueryParam("correlationId") String correlationId,
        @QueryParam("requestAck")    boolean requestAck) {

        ResponseApi<Template> previewResponse = templateAPI.preview(new TemplateParams(templateId, messageId, correlationId, requestAck));
        return  BuildHelper.buildResponse(previewResponse, Response.Status.INTERNAL_SERVER_ERROR);
    }

    @POST
    @Path("{templateId}")
    public Response send(@PathParam("templateId") String templateId, JsonNode msgWithParams) {

        logger.debug("sending template: {} with param: {}", templateId, msgWithParams);
        ResponseApi<MessageResponse> sendResponse = messageAPI.send(templateId, msgWithParams);
        return BuildHelper.buildResponse(sendResponse, Response.Status.INTERNAL_SERVER_ERROR, Response.Status.CREATED);
    }

}
