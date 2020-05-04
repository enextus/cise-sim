package eu.cise.sim.exceptions;

public class NullSignatureServiceEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The signature service parameter passed to the " +
            "sim engine object is null";

    public NullSignatureServiceEx() {
        super(EXCEPTION_MESSAGE);
    }

}
