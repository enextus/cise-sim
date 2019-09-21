package eu.cise.emulator.exceptions;

public class NullClockEx extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "The clock parameter passed to the" +
            "emulator engine object is null";

    public NullClockEx() {
        super(EXCEPTION_MESSAGE);
    }

}
