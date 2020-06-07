package eu.cise.sim.api.messages;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.SendResponse;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.label.IncidentMessageLabelDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/ui/messages")
@Produces(MediaType.APPLICATION_JSON)
public class UiMessageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UiMessageResource.class);
    private final MessageAPI messageAPI;
    private final MessageService messageService;

    public UiMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;
        this.messageService = new MessageService();
    }

    @POST
    @Consumes({"application/xml", "text/plain", "text/xml"})
    @Produces("application/xml")
    public Response receive(String inputXmlMessage) {

        Acknowledgement acknowledgement = messageAPI.receive(inputXmlMessage);
        return Response
                .status(Response.Status.CREATED)
                .entity(acknowledgement)
                .build();
    }

    @Path("/incident/values")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabelsIncident() {

        return Response
                .status(Response.Status.OK)
                .entity(IncidentMessageLabelDto.getInstance())
                .build();
    }

    @Path("/incident/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendIncident(IncidentRequestDto incidentMsg) {

        LOGGER.info("sendIncident received " + incidentMsg);

        try {
            Message message = messageService.buildIncidentMsg(incidentMsg);
            SendResponse response = messageAPI.send(message);

        } catch (IOException e) {
            LOGGER.warn("sendIncident exception", e);
        }

        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }

}
