package eu.cise.sim.exceptions;

public class CreationDateErrorEx extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "The creation date is outside the allowed range.";

    public CreationDateErrorEx() {
        super(EXCEPTION_MESSAGE);
    }
}
