package eu.cise.emulator.app.resources;

import eu.cise.emulator.app.candidate.Message;
import eu.cise.emulator.app.core.InstanceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Path("/sim-LSA/rest/CISEMessageServiceREST")
@Produces(MediaType.TEXT_PLAIN)
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MessageRestProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRestProcess.class);

    private final InstanceID instance;
    private final AtomicLong counter;

    public MessageRestProcess(InstanceID instance) {
        this.instance = instance ;
        this.counter = new AtomicLong();
    }


    @GET
    @Path("ping")
    public String getServerTime() {
        System.out.println("RESTful Service 'MessageService' is running ==> ping");
        return "received ping on "+new Date().toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Message getMessageResponseInJSON(@QueryParam("body") String something) {
        Message content= new Message();
        content.setBody(""+something);
        content.setId(1);
        return content; //do not use Response object because this causes issues when generating XML automatically

    }


    @POST
    public Message createMessageResponseInXml( Message saying) {
        LOGGER.info("Received a message: {}", saying.getBody());
        return saying;
    }

}
