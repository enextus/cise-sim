package eu.cise.emulator.httptransport;


import eu.cise.emulator.integration.Validation.AcceptanceResponse;

@SuppressWarnings("WeakerAccess")
public interface treatIncomingAgent {

    AcceptanceResponse accept(String parameter);
}
