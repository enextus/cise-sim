package eu.cise.emulator.httptransport;

import eu.cise.emulator.httptransport.Validation.AcceptanceAgent;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


@Path("/rest")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class ServerRestConcrete implements ServerRest {
    public static ServerRestConcrete instance;

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;
    AcceptanceAgent messageManager;

    public ServerRestConcrete(String id, AcceptanceAgent messageManager) {
        setupServerRestConcrete(id, messageManager);
    }

    static ServerRestConcrete getInstance() {
        return instance;
    }

    public void setupServerRestConcrete(String id, AcceptanceAgent messageManager) {
        instance = this;
        if (!instance.isStarted()) {
            instance.id = id;
            this.messageManager = messageManager;
            this.isStarted();
        }
    }

    // for the browser
    @POST()
    @Path("/sec/messages")
    public Acknowledgement create(Message receivedMessage) {

        Acknowledgement returnnmessage = messageManager.treatIncomingMessage(receivedMessage);

        if (receivedMessage == null)
            throw new RuntimeException("Error with Post: message body not found");
        if (returnnmessage == null)
            throw new RuntimeException("Error with Post: treated message result in non valid body");
        return returnnmessage;
    }

    @Override
    public boolean isStarted() {
        return false;
    }

}