package eu.cise.emulator.exceptions;

public class NullConfigEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The config parameter passed to the" +
            "emulator engine object is null";

    public NullConfigEx() {
        super(EXCEPTION_MESSAGE);
    }

}
