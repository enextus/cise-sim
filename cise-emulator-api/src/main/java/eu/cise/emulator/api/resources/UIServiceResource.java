package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.ServiceDetail;
import eu.cise.emulator.EmuConfig;
import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ui/service")
@Produces(MediaType.APPLICATION_JSON)
public class UIServiceResource {
    private final EmuConfig emuConfig;

    public UIServiceResource(EmuConfig emuConfig) {
        this.emuConfig = emuConfig;
    }

    @GET
    @Path("/self")
    public Response informSelfInfo() {
        Participant participant = new Participant();
        participant.setId(emuConfig.simulatorName());

        Service service = new Service();
        service.setParticipant(participant);

        ServiceDetail serviceDetail = new ServiceDetail(
            service,
            emuConfig.destinationProtocol(),
            emuConfig.destinationUrl(),
            emuConfig.appVersion()
        );

        return Response
                .status(Response.Status.OK)
                .entity(serviceDetail)
                .build();
    }

}
