package eu.cise.emulator.deprecated.cli.core.sub;


import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

@SuppressWarnings("WeakerAccess")
public interface AcceptanceAgent {
    AcceptanceResponse accept(String parameter);

    Acknowledgement treatIncomingMessage(Message receivedMessage);
}
