package eu.cise.dispatcher.rest;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.engine.DispatchResult;
import eu.cise.sim.engine.Dispatcher;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RestDispatcher performs RESTful request to nodes or legacy systems. The current
 * implementation is just sending CISE Messages
 */
@SuppressWarnings({"WeakerAccess", "Unused"})
public class RestDispatcher implements Dispatcher {

    private final Logger logger;
    private final RestClient client;
    /**
     * NOTE: This mapper must be not validating
     */
    private final XmlMapper xmlMapper;

    /**
     * This constructor is called by the class for name
     *
     * @param prettyNotValidatingXmlMapper
     */
    @SuppressWarnings("unused")
    public RestDispatcher(XmlMapper prettyNotValidatingXmlMapper) {
        this(new JerseyRestClient(), prettyNotValidatingXmlMapper);
    }

    public RestDispatcher(RestClient client, XmlMapper xmlMapper) {
        this.client = client;
        this.xmlMapper = xmlMapper;
        this.logger = LoggerFactory.getLogger(RestDispatcher.class);
    }

    /**
     * The main responsibility of the class is to send messages by using a RESTful client.
     *
     * @param message message to be sent.
     * @param address gateway address to send the message to
     * @return a {@link DispatchResult} containing the dispatching results
     */
    @Override
    public DispatchResult send(Message message, String address) {
        String payload = xmlMapper.toXML(message);

        if (logger.isDebugEnabled()) {
            logger.debug("> sending message");
            logger.debug("> address: {}", address);
            logger.debug("> payload: \n{}\n", payload);
        } else {
            logger.info("Sending Rest message to " + address);
        }

        RestResult result = client.post(address, payload);

        if (logger.isDebugEnabled()) {
            logger.debug("< server response: {}", result);
        } else {
            logger.info("Server response: isOk[{}], code[{}], message[{}]", result.isOK(), result.getCode(), result.getMessage());
        }
        return new DispatchResult(result.isOK(), xmlMapper.fromXML(result.getBody()));
    }

}