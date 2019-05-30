package eu.cise.sim.transport.conformance;
import eu.cise.sim.transport.Exception.CiseTransportException;
import eu.eucise.servicemodel.v1.message.Message;

/**
 * It's the message entry point. When a gateway receives a message it's
 * accepted and validated by this class.
 * <p>
 * The message will check if:
 * - the string contains a valid XML paring it into a message
 * - add the gateway sender if necessary
 * - pass the parsed message to the GatewayProcessor
 */
@FunctionalInterface
public interface SubmissionAgent {

    /**
     * The unique entry point of the object that will check the incoming message
     *
     * @param message the payload to be parsed, validated, enriched and
     *                   processed
     * @throws CiseTransportException in case of any error
     */
    void forward(Message message);
}
