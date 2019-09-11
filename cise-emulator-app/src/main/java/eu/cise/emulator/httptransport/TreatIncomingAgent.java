package eu.cise.emulator.httptransport;


import eu.cise.emulator.integration.Validation.AcceptanceResponse;

@SuppressWarnings("WeakerAccess")
public interface TreatIncomingAgent {

    AcceptanceResponse accept(String parameter);
}
