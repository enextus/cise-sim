package eu.cise.emulator;

import eu.cise.emulator.exceptions.EndpointErrorEx;
import eu.cise.emulator.exceptions.EndpointNotFoundEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public interface MessageProcessor {
    Message preview(Message message, SendParam param);

    Acknowledgement send(Message message, SendParam param) throws EndpointNotFoundEx, EndpointErrorEx;
}
