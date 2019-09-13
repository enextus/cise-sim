package eu.cise.emulator.httptransport;

import eu.cise.servicemodel.v1.message.Message;

/**
 * Dispatcher interface will abstract from the protocols and details used to
 * transfer messages from one system to another.
 */
public interface Dispatcher {

    /**
     * This method will send a string payload to a given string address. The
     * protocol used could be REST, SOAP, Domibus Endpoints or other solutions
     *
     * @param message the message to be sent
     * @param address the conformance address to send the message to
     * @return a response object with the status and body returned by the
     * counterpart
     */
    DispatchResult send(Message message, String address);

}