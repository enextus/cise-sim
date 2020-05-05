package eu.cise.sim.api.history;

import eu.cise.sim.api.dto.MessageShortInfoDto;
import org.apache.commons.collections.CollectionUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/history")
@Produces(MediaType.APPLICATION_JSON)
public class HistoryResource {

    private final HistoryAPI historyAPI;

    public HistoryResource(HistoryAPI historyAPI) {
        this.historyAPI = historyAPI;
    }

    @Path("/latest")
    @GET
    public Response pullAndDelete() {

        List<MessageShortInfoDto> lastStoredMessage = historyAPI.getLatestMessages();

        Response response;
        if (CollectionUtils.isEmpty(lastStoredMessage)) {
            response = Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(lastStoredMessage)
                    .build();
        }

        return response;
    }
}
