package eu.cise.sim.transport;

import eu.eucise.servicemodel.v1.message.Message;
import eu.cise.sim.transport.Validation.AbsValidator;

import java.util.function.Consumer;


class DefaultValidator<T extends Message> extends AbsValidator {

    DefaultValidator(T message) {
        super( message);
    }

    @Override
    public void validates(Message message, Consumer validator) {
        if (isServiceIdNull(message.getRecipient())){

        }
    }

    @Override
    public void destinationAddressingCheck() {
        if (isServiceIdNull(message.getRecipient())){

        }
    }
}
