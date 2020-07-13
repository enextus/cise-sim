package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;

public enum MessageTypeEnum {

    PUSH("Push", "PUSH"),
    PULL_RESPONSE("Pull Response", "PULLRESPONSE"),
    PULL_REQUEST("Pull Request", "PULLREQUEST"),
    FEEDBACK("Feedback", "FEEDBACK"),
    ACK_SYNC("Ack Synch", "ACKSYNCH"),
    ACK_ASYNC("Ack Asynch", "ACKASYNCH"),
    PUBLISH("Publish", "PUBLISH"),
    SUBSCRIBE("Subscribe", "SUBSCRIBE");


    private final String uiName;
    private final String fileName;

    MessageTypeEnum(String uiName, String fileName) {
        this.uiName = uiName;
        this.fileName = fileName;
    }

    public static MessageTypeEnum valueOf(Message message) throws IllegalArgumentException {

        if (message instanceof PullRequest) {
            // Suscribe is a pull request with <ServiceOperation>Subscribe</ServiceOperation>
            return (message.getSender().getServiceOperation() == ServiceOperationType.SUBSCRIBE) ?
                    SUBSCRIBE :
                    PULL_REQUEST;
        }


        if (message instanceof PullResponse) {
            return PULL_RESPONSE;
        }

        if (message instanceof Push) {
            // Publish is Push with <ServiceOperation>Subscribe</ServiceOperation>
            return (message.getSender().getServiceOperation() == ServiceOperationType.SUBSCRIBE) ?
                    PUBLISH :
                    PUSH;
        }

        if (message instanceof Feedback) {
            return FEEDBACK;
        }

        if (message instanceof Acknowledgement) {
            if (message.getSender() == null) {
                return ACK_SYNC;
            } else {
                return ACK_ASYNC;
            }
        }

        throw new IllegalArgumentException("Message class is unknown " + message.getClass().getCanonicalName());
    }

    public String getUiName() {
        return uiName;
    }

    public String getFileName() {
        return fileName;
    }
}
