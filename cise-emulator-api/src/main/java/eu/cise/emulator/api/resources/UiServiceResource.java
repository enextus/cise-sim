package eu.cise.emulator.send.resources;

import eu.cise.ServiceDetail;
import eu.cise.emulator.EmuConfig;
import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ui/service")
@Produces(MediaType.APPLICATION_JSON)
public class UiServiceResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UiServiceResource.class);
    private final EmuConfig emuconfig;


    public UiServiceResource(EmuConfig emuConfig) {
        this.emuconfig = emuConfig;
    }

    @Path("/self")
    @GET
    public Response informSelfInfo() {

        LOGGER.info("informSelfInfo was called  : service.participant =  "
                + emuconfig.participantId());
        Participant participant = new Participant();
        participant.setId(emuconfig.participantId());
        Service service = new Service();
        service.setParticipant(participant);
        ServiceDetail serviceDef = new ServiceDetail(service, emuconfig.dispatcherType());
        return Response
                .status(Response.Status.OK)
                .entity(serviceDef)
                .build();

    }

}
