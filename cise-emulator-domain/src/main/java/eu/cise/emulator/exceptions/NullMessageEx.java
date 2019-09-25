package eu.cise.emulator.exceptions;

public class NullMessageEx  extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The message passed to a receive method is null.";

    public NullMessageEx() {
        super(EXCEPTION_MESSAGE);
    }
}
