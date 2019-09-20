package eu.cise.emulator.exceptions;

public class EndpointNotFoundEx extends Exception {
    private static final String EXCEPTION_MESSAGE = "Destination endpoint not found.";

    public EndpointNotFoundEx() {
        super(EXCEPTION_MESSAGE);
    }

}
