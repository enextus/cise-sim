package eu.cise.sim.api.rest;

import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.sim.api.ServiceDetail;
import eu.cise.sim.config.SimConfig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ui/service")
@Produces(MediaType.APPLICATION_JSON)
public class UIServiceResource {
    private final SimConfig simConfig;

    public UIServiceResource(SimConfig simConfig) {
        this.simConfig = simConfig;
    }

    @GET
    @Path("/self")
    public Response informSelfInfo() {
        Participant participant = new Participant();
        participant.setId(simConfig.simulatorName());

        Service service = new Service();
        service.setParticipant(participant);

        ServiceDetail serviceDetail = new ServiceDetail(
            service,
            simConfig.destinationProtocol(),
            simConfig.destinationUrl(),
            simConfig.appVersion(),
            simConfig.guiMaxHistoryShow());


        return Response
                .status(Response.Status.OK)
                .entity(serviceDetail)
                .build();
    }

}
