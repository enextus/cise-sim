package eu.cise.emulator;

import static java.lang.String.format;

public class EmulatorEx extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public EmulatorEx(Throwable cause) {
        super(cause);
    }

    public EmulatorEx(String message, Object... args) {
        super(format(message, args));
    }

    public EmulatorEx(String message, Throwable t, Object... args) {
        super(format(message, args), t);
    }

    @Override
    public String toString() {
        return format("[%s]=%s", getClass().getSimpleName(), getMessage());
    }
}
