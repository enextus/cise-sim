package eu.cise.emulator.httptransport.Validation;

import eu.cise.emulator.httptransport.Exception.IllegalMessageException;
import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.service.Service;

import java.util.Optional;

abstract public class AbsValidator<T extends Message> implements Validator {

    final public T message;

    public AbsValidator(T message) {
        this.message = message;
    }



    @Override
    public void messageNotNullCheck() {
        Optional.ofNullable(message)
                .orElseThrow(() -> new IllegalMessageException(IllegalMessageException.NULL_MESSAGE));
    }

    @Override
    public void senderIdNotNullCheck() {
        Optional.ofNullable(message)
                .map(Message::getSender)
                .map(Service::getServiceID)
                .orElseThrow(() -> new IllegalMessageException(IllegalMessageException.SENDER_ID_NOT_SPECIFIED));
    }

    @Override
    public void senderAddressNotNullCheck() {
        Optional.ofNullable(message)
                .map(Message::getSender)
                .map(Service::getParticipant)
                .map(Participant::getEndpointUrl)
                .orElseThrow(() -> new IllegalMessageException(IllegalMessageException.SENDER_ADDRESS_NOT_SPECIFIED));
    }

    @Override
    public abstract void destinationAddressingCheck();

    protected boolean isServiceIdNull(Service recipient) {
        return recipient == null || recipient.getServiceID() == null;
    }
}
