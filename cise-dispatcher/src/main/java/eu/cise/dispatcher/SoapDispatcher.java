package eu.cise.dispatcher;

import eu.cise.servicemodel.v1.message.Message;

public class SoapDispatcher implements Dispatcher {
    @Override
    public DispatchResult send(Message message, String address) {
        return new DispatchResult(false,"");
    }
}
