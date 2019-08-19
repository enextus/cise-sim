package eu.europa.ec.jrc.marex.core.sub;


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
            return (Validator) new PullRequestValidator((PullRequest) message);
        } else if (message instanceof Push) {
            return (Validator) new PushValidator((Push) message);
        }

        return (Validator)  new PushValidator((Push) message); //*type fail**/
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