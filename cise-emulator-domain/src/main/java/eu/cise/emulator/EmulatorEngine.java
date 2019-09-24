package eu.cise.emulator;

import eu.cise.emulator.exceptions.EndpointErrorEx;
import eu.cise.emulator.exceptions.EndpointNotFoundEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

/**
 * This is the engine of the emulator that holds all the main
 * flows of business logic for preparing,  sending, receiving
 * interpreting the received message and act on it appropriately
 */
public interface EmulatorEngine {

    /**
     * Prepare the message before sending it by modifying some
     * properties of it
     *
     * @param message the template message
     * @param param the object holding the parameters to be modified
     * @return the modified message
     */
    <T extends Message> T prepare(T message, SendParam param);

    /**
     * It sends the message
     *
     * @param message message to be sent
     * @return a sync acknowledgment received
     * TODO specify the runtime exceptions to be captured by the client
     */
    Acknowledgement send(Message message) throws EndpointNotFoundEx, EndpointErrorEx;

    void receive(Message message);
}
