package eu.cise.emulator.integration.Validation;


import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.Push;

import java.util.function.Consumer;

public class MessageValidator implements Validator {

    @Override
    public void validates(Message message, Consumer<Validator> validator) {

        validator.accept(create(message));
    }

    private Validator create(Message message) {
        if (message instanceof PullRequest) {
            return new PullRequestValidator((PullRequest) message);
        } else if (message instanceof Push) {
            return new PushValidator((Push) message);
        }

        return new PushValidator((Push) message); //*type fail**/
    }

    @Override
    public void messageNotNullCheck() {

    }

    @Override
    public void senderIdNotNullCheck() {

    }

    @Override
    public void senderAddressNotNullCheck() {

    }

    @Override
    public void destinationAddressingCheck() {

    }
}