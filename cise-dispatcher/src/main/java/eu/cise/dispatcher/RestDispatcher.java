package eu.cise.dispatcher;

import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;

/**
 * The RestDispatcher performs RESTful request to nodes or legacy systems. The current
 * implementation is just sending CISE Messages
 */
@SuppressWarnings({"WeakerAccess", "Unused"})
public class RestDispatcher implements Dispatcher {

    private final RestClient client;
    /**
        NOTE: This mapper must be not validating
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
    }

    /**
     * The main responsibility of the class is to send messages by using a RESTful client.
     *
     * @param message message to be sent.
     * @param address gateway address to send the message to
     * @return a {@link eu.cise.dispatcher.DispatchResult} containing the dispatching results
     */
    @Override
    public DispatchResult send(Message message, String address) {
        String payload = xmlMapper.toXML(message);
        System.out.println("----------------------------\n" + payload + "\n----------------------------");
        RestResult result = client.post(address, payload);

        return new DispatchResult(result.isOK(), xmlMapper.fromXML(result.getBody()));
    }

}
