package eu.cise.emulator.exceptions;

public class NullSignatureServiceEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The signature service parameter passed to the" +
            "emulator engine object is null";

    public NullSignatureServiceEx() {
        super(EXCEPTION_MESSAGE);
    }

}
