package eu.europa.ec.jrc.marex.core.sub;


@SuppressWarnings("WeakerAccess")
public interface TreatIncomingAgent {

    AcceptanceResponse accept(String parameter);
}
