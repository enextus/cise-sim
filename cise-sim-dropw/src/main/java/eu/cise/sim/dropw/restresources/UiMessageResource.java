package eu.cise.sim.dropw.restresources;


import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.MessageResponse;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.helpers.BuildHelper;
import eu.cise.sim.api.messages.MessageService;
import eu.cise.sim.api.messages.dto.discovery.DiscoveryRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

        ResponseApi<String> acknowledgement = messageAPI.receiveXML(inputXmlMessage);
        return BuildHelper.buildResponse(acknowledgement, Response.Status.INTERNAL_SERVER_ERROR, Response.Status.CREATED);
    }

    /* ----------- INCIDENT ----------- */

    @Path("/incident/values")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabelsIncident() {

        return Response
                .status(Response.Status.OK)
                .entity(messageService.getLabelsIncident())
                .build();
    }

    @Path("/incident/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendIncident(IncidentRequestDto incidentMsg) {

        LOGGER.info("sendIncident received " + incidentMsg);

        ResponseApi<Message> message = messageService.buildIncident(incidentMsg);
        if (message.isOk()) {
            ResponseApi<MessageResponse> response = messageAPI.send(message.getResult());
        }

        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }

    /* ----------- DISCOVERY ----------- */

    @Path("/discovery/values")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabelsDiscovery() {

        return Response
                .status(Response.Status.OK)
                .entity(messageService.getLabelsDiscovery())
                .build();
    }

    @Path("/discovery/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendDiscovery(DiscoveryRequestDto discoveryMsg) {

        LOGGER.info("sendDiscovery received " + discoveryMsg);

        ResponseApi<Message> message = messageService.buildDiscovery(discoveryMsg);
        if (message.isOk()) {
            ResponseApi<MessageResponse> response = messageAPI.send(message.getResult());

            if (response.isOk()) {
                messageService.manageDiscoveryAnswer(response.getResult().getAcknowledgement());
            }
        }

        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }
}
