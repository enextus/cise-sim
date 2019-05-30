package eu.cise.sim.transport;


import eu.cise.sim.transport.conformance.AcceptanceResponse;

@SuppressWarnings("WeakerAccess")
public interface treatIncomingAgent {

    AcceptanceResponse accept(String parameter);
}
