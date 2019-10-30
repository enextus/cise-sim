package eu.cise.dispatcher;

import eu.cise.dispatcher.soap.CISEMessageService;
import eu.cise.dispatcher.soap.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;

import java.net.MalformedURLException;
import java.net.URL;

public class SoapDispatcher implements Dispatcher {

    @Override
    public DispatchResult send(Message message, String address) {
        URL soapEndpoint = null;
        try {
            soapEndpoint = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CISEMessageServiceSoapImpl service = new CISEMessageService(soapEndpoint).getCISEMessageServiceSoapPort();

        Acknowledgement ack = service.send(message);

        if (ack.getAckCode() == AcknowledgementType.SUCCESS) {
            return new DispatchResult(true, ack);
        } else {
            return new DispatchResult(false, ack);
        }
    }
}
