package eu.cise.sim.transport.conformance;


@SuppressWarnings("WeakerAccess")
public interface AcceptanceAgent {
    AcceptanceResponse accept(String parameter);

    Acknowledgement treatIncomingMessage(Message receivedMessage);
}
