package eu.cise.emulator.exceptions;

public class NullMessageIdEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The messageId passed to a SendParam is null.";

    public NullMessageIdEx() {
        super(EXCEPTION_MESSAGE);
    }

}
