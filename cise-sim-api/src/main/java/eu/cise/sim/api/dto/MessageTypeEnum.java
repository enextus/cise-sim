package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.*;

public enum MessageTypeEnum {

    PUSH, PULL_RESPONSE, PULL_REQUEST, FEEDBACK, ACKNOWLEDGEMENT;

    public static MessageTypeEnum valueOf(Message message) throws IllegalArgumentException {

        MessageTypeEnum result;

        if (message instanceof PullRequest) {
            result = PULL_REQUEST;
        } else if (message instanceof PullResponse) {
            result = PULL_RESPONSE;
        } else if (message instanceof Push) {
            result = PUSH;
        } else if (message instanceof Feedback) {
            result = FEEDBACK;
        } else if (message instanceof Acknowledgement) {
            result = ACKNOWLEDGEMENT;
        } else {
            throw new IllegalArgumentException("Message class is unknown " + message.getClass().getCanonicalName());
        }

        return result;
    }
}
