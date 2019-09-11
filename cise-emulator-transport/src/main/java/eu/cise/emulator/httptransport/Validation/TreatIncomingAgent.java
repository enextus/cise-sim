package eu.cise.emulator.httptransport.Validation;


@SuppressWarnings("WeakerAccess")
public interface TreatIncomingAgent {
    AcceptanceResponse accept(String parameter);
}
