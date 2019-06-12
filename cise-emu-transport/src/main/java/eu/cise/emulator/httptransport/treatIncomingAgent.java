package eu.cise.emulator.httptransport;


import eu.cise.emulator.httptransport.conformance.AcceptanceResponse;

@SuppressWarnings("WeakerAccess")
public interface treatIncomingAgent {

    AcceptanceResponse accept(String parameter);
}
