package eu.cise.sim.dropw.restresources;


import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.api.helpers.BuildHelper;
import eu.cise.sim.api.history.ThreadMessageAPI;
import org.apache.commons.collections.CollectionUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ui/history")
public class ThreadMessageResource {

    private final ThreadMessageAPI threadMessageAPI;

    public ThreadMessageResource(ThreadMessageAPI threadMessageAPI) {
        this.threadMessageAPI = threadMessageAPI;
    }


    @Path("/latest/{timestamp}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestMessages(@PathParam("timestamp") long timestamp) {

        List<MessageShortInfoDto> lastStoredMessage = threadMessageAPI.getThreadsAfter(timestamp);

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

        ResponseApi<String> xmlMessage = threadMessageAPI.getXmlMessageByUuid(uuid);
        return BuildHelper.buildResponse(xmlMessage, Response.Status.NO_CONTENT);
    }
}
