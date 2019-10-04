package eu.cise.emulator.deprecated.web.httptransport;


import eu.cise.emulator.deprecated.web.integration.Validation.AcceptanceResponse;

@SuppressWarnings("WeakerAccess")
public interface TreatIncomingAgent {

    AcceptanceResponse accept(String parameter);
}
