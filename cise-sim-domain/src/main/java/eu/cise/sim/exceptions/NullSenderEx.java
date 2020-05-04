package eu.cise.sim.exceptions;

public class NullSenderEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The sender of the message passed can't be null.";

    public NullSenderEx() {
        super(EXCEPTION_MESSAGE);
    }

}
