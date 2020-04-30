package eu.cise.sim.exceptions;

public class EmptyMessageIdEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The messageId passed is empty (null or blank).";

    public EmptyMessageIdEx() {
        super(EXCEPTION_MESSAGE);
    }

}
