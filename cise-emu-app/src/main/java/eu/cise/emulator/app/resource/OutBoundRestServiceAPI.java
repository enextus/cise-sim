package eu.cise.emulator.app.resource;

import ch.qos.logback.classic.Logger;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metric;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.app.candidate.Message;
import eu.cise.emulator.app.candidate.MessageType;
import eu.cise.emulator.app.core.InstanceID;
import eu.cise.emulator.app.transport.OutBoundWebSocketClient;
import eu.cise.emulator.app.candidate.User;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;


@Path("webapi")
public class OutBoundRestServiceAPI {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = (Logger) LoggerFactory.getLogger(OutBoundRestServiceAPI.class.getName());
    private final String version;
    private final InstanceID instanceID;

    public OutBoundRestServiceAPI(String version, InstanceID instanceID) {
        this.version = version;
        this.instanceID = instanceID;
    }


    @GET
    @Metric
    @Produces("application/json")
    @Path("/activemember")
    public String getReturnMemberId() throws Exception {
        User activeMember = new User();
        activeMember.setId(instanceID.getNumId() + "@" + version);
        activeMember.setName(instanceID.getName());
        String messageJson = "#Undefined#";
        try {
            messageJson = getMapper().writeValueAsString(activeMember);
        } catch (JsonProcessingException ex) {
            getLogger().error("json exception: " + ex.getMessage());
        }
        return (messageJson);
    }

    @Metric
    @ExceptionMetered
    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    @Path("/returnmessage")
    public void returnBytheWebsocket() {

        Message amessage = new Message();
        amessage.setData("");
        amessage.setUser(new User());
        amessage.getUser().setId("23");
        amessage.getUser().setName("joe");
        amessage.setType(MessageType.TEXT_MESSAGE);

        try {

            String messageJson = getMapper().writeValueAsString(amessage);
            OutBoundWebSocketClient aclient = OutBoundWebSocketClient.build();
            aclient.addMessageHandler(new OutBoundWebSocketClient.MessageHandler() {
                public void handleMessage(String message) {
                    getLogger().info("!!!!!returnedMessageByWebsocket = success!!!!!!!");
                }
            });
            // send message to websocket
            aclient.sendMessage(messageJson);
            // wait 5 seconds for messages from websocket
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            getLogger().error("InterruptedException exception: " + ex.getMessage());
        } catch (JsonProcessingException ex2) {
            getLogger().error("json exception: " + ex2.getMessage());
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public Logger getLogger() {
        return logger;
    }
}