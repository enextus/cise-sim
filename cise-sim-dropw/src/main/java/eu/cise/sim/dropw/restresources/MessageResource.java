package eu.cise.sim.dropw.restresources;

import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.helpers.BuildHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/messages")
public class MessageResource {

    public static final String ERROR = "ERROR";
    private final MessageAPI messageAPI;


    public MessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;
    }

    @POST
    @Consumes({"application/xml", "text/plain", "text/xml"})
    @Produces("application/xml")
    public Response receive(String inputXmlMessage) {

        ResponseApi<String> result = messageAPI.receiveXML(inputXmlMessage);
        return BuildHelper.buildResponse(result, Response.Status.INTERNAL_SERVER_ERROR, Response.Status.CREATED);
    }

}
