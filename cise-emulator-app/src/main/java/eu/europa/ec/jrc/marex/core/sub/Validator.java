package eu.europa.ec.jrc.marex.core.sub;

import eu.cise.servicemodel.v1.message.Message;

import java.util.function.Consumer;

public interface Validator {
    void validates(Message message, Consumer<Validator> validator);

    void messageNotNullCheck();

    void senderIdNotNullCheck();

    void senderAddressNotNullCheck();

    void destinationAddressingCheck();
}
