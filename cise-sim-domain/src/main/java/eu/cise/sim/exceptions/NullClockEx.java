package eu.cise.sim.exceptions;

public class NullClockEx extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "The clock parameter passed to the" +
            "sim engine object is null";

    public NullClockEx() {
        super(EXCEPTION_MESSAGE);
    }

}
