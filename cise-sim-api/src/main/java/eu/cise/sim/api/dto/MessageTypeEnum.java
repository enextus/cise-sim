package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.*;

public enum MessageTypeEnum {

    PUSH("Push", "PUSH"),
    PULL_RESPONSE("Pull Response", "PULLRESPONSE"),
    PULL_REQUEST("Pull Request", "PULLREQUEST"),
    FEEDBACK("Feedback", "FEEDBACK"),
    ACK_SYNC("Ack Synch", "ACKSYNCH"),
    ACK_ASYNC("Ack Asynch", "ACKASYNCH");

    private final String uiName;
    private final String fileName;

    MessageTypeEnum(String uiName, String fileName) {
        this.uiName = uiName;
        this.fileName = fileName;
    }

    public static MessageTypeEnum valueOf(Message message) throws IllegalArgumentException {

        if (message instanceof PullRequest) {
            return PULL_REQUEST;
        }

        if (message instanceof PullResponse) {
            return PULL_RESPONSE;
        }

        if (message instanceof Push) {
            return PUSH;
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
