package eu.cise.emulator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DefaultMessageResource {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Path("/messages")
    @POST
    public Response messageCreate(String contentPosted){
        logger.info("this is what received :"+contentPosted);
        return Response.status(Response.Status.CREATED).build();
    }


}
