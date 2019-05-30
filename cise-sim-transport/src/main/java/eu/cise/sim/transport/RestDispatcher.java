package eu.cise.sim.transport;


import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.DefaultXmlValidator;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlValidator;


/**
 * This class is meant to perform RESTful request to nodes or legacy systems.
 * The current implementation is just sending CISE Messages
 */
@SuppressWarnings({"WeakerAccess", "Unused"})
public class RestDispatcher implements Dispatcher {

    private final RestClient client;
    private final XmlMapper xmlMapper;
    private final XmlValidator xmlValidator;

    /**
     * This constructor is called by the class for name
     */
    @SuppressWarnings("unused")
    public RestDispatcher() {
        this(new JerseyRestClient(), new DefaultXmlMapper.NotValidating(), new DefaultXmlValidator());
    }

    public RestDispatcher(RestClient client,
                          XmlMapper xmlMapper, XmlValidator xmlValidator) {
        this.client = client;
        this.xmlMapper = xmlMapper;
        this.xmlValidator = xmlValidator;
    }

    @Override
    public DispatchResult send(Message message, String address) {
        String payload = xmlMapper.toXML(message);
        xmlValidator.validate(payload);

        RestResult result = client.post(address, payload);

        return new DispatchResult(result.isOK(), result.getBody());
    }

}


//*REPENTIR **/
//*package jrc.cise.transport;*//