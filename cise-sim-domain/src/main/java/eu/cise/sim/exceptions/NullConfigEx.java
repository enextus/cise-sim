package eu.cise.sim.exceptions;

public class NullConfigEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The config parameter passed to the" +
            "sim engine object is null";

    public NullConfigEx() {
        super(EXCEPTION_MESSAGE);
    }

}
