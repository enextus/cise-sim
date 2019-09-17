package eu.cise.dispatcher;

/**
 * This is an interface to define an adapter of a RESTful client in order to
 * communicate with external services.
 *<p>
 * Creating an adapter is important in order to use any kind of HTTP client
 * is desired or necessary to create the actual connection.
 */
@SuppressWarnings("unused")
public interface RestClient {

    /**
     * A method to perform POST requests to a server connecting to an address
     * transmitting  a string payload
     *
     * @param address the address to contact to deliver the request
     * @param payload the payload to be delivered
     * @return the {@link eu.cise.dispatcher.RestResult}
     */
    RestResult post(String address, String payload);

    /**
     * A method to perform GET requests to a server address
     *
     * @param address the address to contact to deliver the request
     * @return the {@link eu.cise.dispatcher.RestResult}
     */
    RestResult get(String address);

    /**
     * A method to perform DELETE requests to a server address
     *
     * @param address the address to contact to deliver the request
     * @return the {@link eu.cise.dispatcher.RestResult}
     */
    RestResult delete(String address);

}
