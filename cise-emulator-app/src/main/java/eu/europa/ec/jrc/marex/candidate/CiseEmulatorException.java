package eu.europa.ec.jrc.marex.candidate;

public class CiseEmulatorException extends RuntimeException {
    public CiseEmulatorException(String message) {
        super(message);
    }

    public CiseEmulatorException(String message, Throwable e) {
        super(message, e);
    }
}
