package eu.cise.sim.transport;

import eu.eucise.servicemodel.v1.message.Acknowledgement;
import eu.eucise.servicemodel.v1.message.Message;
import eu.cise.sim.transport.conformance.AcceptanceAgent;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService(name = "transport", serviceName = "soap")
public class ServerSoapConcrete implements Server {
     public static ServerSoapConcrete instance ;
     String id;
     AcceptanceAgent messageManager;



    public void SetupServerSoapConcrete(String id, AcceptanceAgent messageManager) {
        instance = this;
        if (! instance.isStarted()){
            this.instance.id = id;
            this.messageManager = (AcceptanceAgent) messageManager;
            this.isStarted();
        }
    }
    public ServerSoapConcrete() {
    }

    @Override
    public boolean isStarted() {
        return false;
    }


    static ServerSoapConcrete getInstance() {
        return instance;
    }

    @WebMethod(operationName = "message")
    public Message Create(Message receivedMessage) {
        Acknowledgement returnnmessage = messageManager.treatIncomingMessage(receivedMessage);

        if(receivedMessage==null)
            throw new RuntimeException("Error with Post: message body not found");
        if(returnnmessage==null)
            throw new RuntimeException("Error with Post: treated message result in non valid body");
        return returnnmessage;
    }

}

