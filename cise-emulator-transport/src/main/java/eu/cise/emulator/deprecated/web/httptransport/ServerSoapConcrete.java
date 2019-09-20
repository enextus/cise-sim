package eu.cise.emulator.deprecated.web.httptransport;

import eu.cise.emulator.deprecated.web.httptransport.Validation.AcceptanceAgent;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;


// TODO Fix annotation for web services
//@WebService(name = "transport", serviceName = "soap")
public class ServerSoapConcrete implements Server {
    public static ServerSoapConcrete instance;
    String id;
    AcceptanceAgent messageManager;


    public ServerSoapConcrete() {
    }

    static ServerSoapConcrete getInstance() {
        return instance;
    }

    public void setupServerSoapConcrete(String id, AcceptanceAgent messageManager) {
        instance = this;
        if (!instance.isStarted()) {
            this.instance.id = id;
            this.messageManager = (AcceptanceAgent) messageManager;
            this.isStarted();
        }
    }

    @Override
    public boolean isStarted() {
        return false;
    }

    //    @WebMethod(operationName = "message")
    public Message create(Message receivedMessage) {
        Acknowledgement returnnmessage = messageManager.treatIncomingMessage(receivedMessage);

        if (receivedMessage == null)
            throw new RuntimeException("Error with Post: message body not found");
        if (returnnmessage == null)
            throw new RuntimeException("Error with Post: treated message result in non valid body");
        return returnnmessage;
    }

}

