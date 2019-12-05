package eu.cise.dispatcher;

import eu.cise.dispatcher.soap.CISEMessageService;
import eu.cise.dispatcher.soap.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoapDispatcher implements Dispatcher {

    private final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    @Override
    public DispatchResult send(Message message, String address) {

        CISEMessageServiceSoapImpl service =
            new CISEMessageService(getSoapEndpoint(address))
                .getCISEMessageServiceSoapPort();

        logger.debug("> sending message");
        logger.debug("> address: {}", address);
        logger.debug("> payload: \n{}\n", message.toString());
        Acknowledgement ack = service.send(message);
        logger.debug("< server response (ack): {}", ack.toString());

        return new DispatchResult(true, ack);
    }

    private URL getSoapEndpoint(String address) {
        try {
            return new URL(address + "?wsdl");
        } catch (MalformedURLException e) {
            throw new DispatcherException("Malformed URL: " + address, e);
        }
    }
}
