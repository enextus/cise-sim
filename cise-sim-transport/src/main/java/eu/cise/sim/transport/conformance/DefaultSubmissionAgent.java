package eu.cise.sim.transport.conformance;

import eu.cise.sim.transport.Validation.MessageValidator;
import eu.eucise.servicemodel.v1.authority.Participant;
import eu.eucise.servicemodel.v1.message.Message;
import eu.eucise.servicemodel.v1.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSubmissionAgent implements SubmissionAgent {

    private final Logger gatewayLogger  = LoggerFactory.getLogger("eu.cise.sim.transport.DefaultSubmissionAgent");
    private final MessageValidator validator;
    private final GatewayProcessor gatewayProcessor;
    private final String gatewayAddressString;



    public DefaultSubmissionAgent(MessageValidator validator,
                                  GatewayProcessor gatewayProcessor, String gatewayAddressString) {

        this.validator = validator;
        this.gatewayProcessor = gatewayProcessor;
        this.gatewayAddressString = gatewayAddressString;
    }

    @Override
    public void forward(Message message) {
        validator.validates(message, v -> {
            v.messageNotNullCheck();
            v.senderIdNotNullCheck();
            v.destinationAddressingCheck();
        });

        Message validMessage = addCurrentGatewayAsSenderAddressTo(message);

        gatewayLogger.debug(validMessage.toString());

        gatewayProcessor.process(validMessage);
    }


    /**
     * TODO ASSUMPTION
     * When a message is coming from a Legacy System the sender nodeAddress
     * should be written by the current node.
     * <p>
     * When a message is coming from a Gateway the sender nodeAddress should be
     * left as it is.
     * <p>
     * TODO - decide how to understand if the assumption is true
     * TODO - find a way to differentiate messages coming from LS and GW
     * TODO - decide if nodeAddress should be overridden when a msg is sent by a
     * TODO   LS that is not specifying it
     *
     * @param message the incoming message
     * @return the same message with a sender nodeAddress added if not present
     */
    private Message addCurrentGatewayAsSenderAddressTo(Message message) {
        if (message.getSender() == null) {
            message.setSender(new Service());
        }

        if (message.getSender().getParticipant() == null) {
            message.getSender().setParticipant(new Participant());
        }

        if (message.getSender().getParticipant().getEndpointUrl() == null) {
            message.getSender().getParticipant().setEndpointUrl(addressFromConfig());
        }

        return message;
    }

    private String addressFromConfig() {
        return gatewayAddressString;
    }

}
