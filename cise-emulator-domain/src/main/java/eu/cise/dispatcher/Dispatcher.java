package eu.cise.dispatcher;

import eu.cise.servicemodel.v1.message.Message;

/**
 * The {@link Dispatcher} interface will abstract the protocols and the details
 * used to transfer messages from one system to another.
 * <p>
 * The concrete implementation may choose to utilize SOAP or REST or other
 * protocol or adapters.
 */
public interface Dispatcher {

    /**
     * This method will send a string payload to a given string address. The
     * protocol used could be REST, SOAP, Domibus Endpoints or other solutions
     *
     * @param message message to be sent
     * @param address gateway address to send the message to
     * @return a response object with the status and body returned by the
     * counterpart
     */
    DispatchResult send(Message message, String address);

}
