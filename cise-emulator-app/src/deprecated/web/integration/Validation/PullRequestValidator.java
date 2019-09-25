package eu.cise.emulator.deprecated.web.integration.Validation;

import eu.cise.emulator.deprecated.web.integration.Exception.IllegalMessageException;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;

import java.util.function.Consumer;

class PullRequestValidator extends AbsValidator<PullRequest> {

    PullRequestValidator(PullRequest message) {
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
