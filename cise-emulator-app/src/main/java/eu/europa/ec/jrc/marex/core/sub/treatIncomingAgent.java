package eu.europa.ec.jrc.marex.core.sub;


@SuppressWarnings("WeakerAccess")
public interface treatIncomingAgent {

    AcceptanceResponse accept(String parameter);
}
