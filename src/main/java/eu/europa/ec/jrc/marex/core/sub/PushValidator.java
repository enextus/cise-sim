package eu.europa.ec.jrc.marex.core.sub;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;

import java.util.function.Consumer;


class PushValidator extends AbsValidator<Push> {

    PushValidator(Push message) {
        super(message);
    }

    @Override
    public void validates(Message message, Consumer<Validator> validator) {

    }

    @Override
    public void destinationAddressingCheck() {
        if (isServiceIdNull(message.getRecipient()) && message.getDiscoveryProfiles().isEmpty())
            throw new IllegalMessageException(IllegalMessageException.RECIPIENT_ID_AND_PROFILE_NOT_SPECIFIED);

        if (!isServiceIdNull(message.getRecipient()) && !message.getDiscoveryProfiles().isEmpty())
            throw new IllegalMessageException(IllegalMessageException.RECIPIENT_ID_AND_PROFILE_BOTH_SPECIFIED);
    }

}
