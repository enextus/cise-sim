package eu.cise.emulator.deprecated.web.httptransport;

import eu.cise.emulator.deprecated.web.httptransport.Validation.AbsValidator;
import eu.cise.servicemodel.v1.message.Message;

import java.util.function.Consumer;


class DefaultValidator<T extends Message> extends AbsValidator {

    DefaultValidator(T message) {
        super(message);
    }

    @Override
    public void validates(Message message, Consumer validator) {
        if (isServiceIdNull(message.getRecipient())) {

        }
    }

    @Override
    public void destinationAddressingCheck() {
        if (isServiceIdNull(message.getRecipient())) {

        }
    }
}
