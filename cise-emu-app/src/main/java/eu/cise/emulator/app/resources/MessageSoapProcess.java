package eu.cise.emulator.app.resources;

import eu.cise.emulator.app.core.InstanceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Path("/sim-LSA/CISEMessageService")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MessageSoapProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSoapProcess.class);

    private final InstanceID instance;
    private final AtomicLong counter;

    public MessageSoapProcess(InstanceID instance) {
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
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessageResponseInJSON(@QueryParam("?wsdl") String something) {
        String content= MessageSoapWsdlDefinition.getInstance().getContent();
        return content; //do not use Response object because this causes issues when generating XML automatically
    }


    @POST
    public String createMessageResponseInXml( String content) {
        LOGGER.info("Received a message: {}", content);
        return content;
    }

}
