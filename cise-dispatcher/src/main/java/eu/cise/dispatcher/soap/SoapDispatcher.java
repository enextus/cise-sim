package eu.cise.dispatcher.soap;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.engine.DispatchResult;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.exceptions.DispatcherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

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
