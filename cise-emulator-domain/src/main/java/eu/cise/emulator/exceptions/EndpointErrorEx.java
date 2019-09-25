package eu.cise.emulator.exceptions;

public class EndpointErrorEx extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "Destination endpoint returned an error.";

    public EndpointErrorEx() {
        super(EXCEPTION_MESSAGE);
    }

}
