package eu.cise.sim.api.helpers;

public class WebApiEmulatorException extends Exception {
    private final int code;

    public WebApiEmulatorException() {

        this(500);

    }

    public WebApiEmulatorException(int code) {

        this(code, "Error while processing the request", null);

    }

    public WebApiEmulatorException(int code, String message) {

        this(code, message, null);

    }

    public WebApiEmulatorException(int code, String message, Throwable throwable) {

        super(message, throwable);

        this.code = code;

    }

    public int getCode() {

        return code;

    }
}
