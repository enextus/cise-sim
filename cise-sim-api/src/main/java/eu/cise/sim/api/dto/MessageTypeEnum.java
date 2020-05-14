package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.*;

public enum MessageTypeEnum {

    PUSH("Push", "PUSH"),
    PULL_RESPONSE("Pull Response", "PULLRESPONSE"),
    PULL_REQUEST("Pull Request", "PULLREQUEST"),
    FEEDBACK("Feedback", "FEEDBACK"),
    ACK_SYNC("Ack Synch", "ACKSYNCH"),
    ACK_ASYNC("Ack Synch", "ACKASYNCH");

    private final String uiName;
    private final String fileName;

    MessageTypeEnum(String uiName, String fileName) {
        this.uiName = uiName;
        this.fileName = fileName;
    }

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
            if (message.getSender() == null) {
                result = ACK_SYNC;
            } else {
                result = ACK_ASYNC;
            }
        } else {
            throw new IllegalArgumentException("Message class is unknown " + message.getClass().getCanonicalName());
        }

        return result;
    }

    public String getUiName() {
        return uiName;
    }

    public String getFileName() {
        return fileName;
    }
}
