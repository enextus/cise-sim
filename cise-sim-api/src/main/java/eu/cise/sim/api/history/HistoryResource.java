package eu.cise.sim.api.history;


import eu.cise.sim.api.dto.MessageShortInfoDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ui/history")
public class HistoryResource {

    private final HistoryAPI historyAPI;

    public HistoryResource(HistoryAPI historyAPI) {
        this.historyAPI = historyAPI;
    }


    @Path("/latest/{timestamp}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestMessages(@PathParam("timestamp") long timestamp) {

        List<MessageShortInfoDto> lastStoredMessage = historyAPI.getThreadsAfter(timestamp);

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

    @Path("/message/{uuid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getMessageByUuid(@PathParam("uuid") String uuid) {

        String xmlMessage = historyAPI.getXmlMessageByUuid(uuid);

        Response response;
        if (StringUtils.isEmpty(xmlMessage)) {
            response = Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(xmlMessage)
                    .build();
        }

        return response;
    }
}
