package eu.cise.sim.api;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;


public  class MessageResponse {

    private final Message message;
    private final Acknowledgement acknowledgement;

    public MessageResponse(Message message, Acknowledgement acknowledgement) {
        this.message = message;
        this.acknowledgement = acknowledgement;
    }


    public Message getMessage() {
        return message;
    }

    public Acknowledgement getAcknowledgement() {
        return acknowledgement;
    }
}
