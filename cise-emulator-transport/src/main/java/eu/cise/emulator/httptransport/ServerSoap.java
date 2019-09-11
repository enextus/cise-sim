package eu.cise.emulator.httptransport;

public interface ServerSoap extends Server {
    double getQuote(String ticker);

}
