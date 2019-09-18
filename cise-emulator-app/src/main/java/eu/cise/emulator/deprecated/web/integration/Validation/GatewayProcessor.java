package eu.cise.emulator.deprecated.web.integration.Validation;


import eu.cise.servicemodel.v1.message.Message;

/**
 * The GatewayProcessor is the heart of the system that will route messages
 * to legacy systems or external gateway after having discovered their
 * destination through the message discovery
 */
@FunctionalInterface
public interface GatewayProcessor {

    /**
     * A WebSocketMessageType from the CISE Entity Model is processed and routed by this
     * object
     *
     * @param message this is a EntityModel WebSocketMessageType that could represent a
     *                Push, PullRequest, PullResponse, Feedback, Acknowledge
     *                message
     */
    void process(Message message);
}
