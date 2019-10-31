package eu.cise.dispatcher;

import eu.cise.dispatcher.soap.CISEMessageService;
import eu.cise.dispatcher.soap.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class SoapDispatcher implements Dispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    @Override
    public DispatchResult send(Message message, String address) {
        URL soapEndpoint = null;
        try {
            soapEndpoint = new URL(address);
        } catch (MalformedURLException e) {
            LOGGER.error("Malformaed URL: ", e);
            throw new DispatcherException("Malformaed URL: ", e);
        }
        CISEMessageServiceSoapImpl service = new CISEMessageService(soapEndpoint).getCISEMessageServiceSoapPort();

        Acknowledgement ack = service.send(message);

        LOGGER.info("Received ack: {}", ack);

        if (ack.getAckCode() == AcknowledgementType.SUCCESS) {
            return new DispatchResult(true, ack);
        } else {
            return new DispatchResult(false, ack);
        }
    }
}
