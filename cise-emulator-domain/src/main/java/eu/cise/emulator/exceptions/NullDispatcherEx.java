package eu.cise.emulator.exceptions;

public class NullDispatcherEx extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "The dispatcher parameter passed to the " +
            "emulator engine object is null";

    public NullDispatcherEx() {
        super(EXCEPTION_MESSAGE);
    }

}
