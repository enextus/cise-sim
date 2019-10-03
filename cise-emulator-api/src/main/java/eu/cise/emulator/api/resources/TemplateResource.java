package eu.cise.emulator.api.resources;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.PreviewResponse;
import eu.cise.emulator.api.TemplateAPI;
import eu.cise.emulator.api.representation.TemplateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Path("/api/templates")
@Produces(MediaType.APPLICATION_JSON)
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

        List<String> filesList = new ArrayList<>();
        try {
            File folder = new File(emuConfig.templateMessagesDirectory());

            Files.list(Paths.get(folder.getAbsolutePath()))
                    .filter(s -> s.toString().endsWith(".xml"))
                    .sorted()
                    .forEach(e -> filesList.add(e.toFile().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        return Response
                .ok()
                .entity(previewResponse.getTemplate())
                .build();
    }

}
