package eu.cise.sim.api.helpers;

public class WebApiSimException extends Exception {
    private final int code;

    public WebApiSimException() {

        this(500);

    }

    public WebApiSimException(int code) {

        this(code, "Error while processing the request", null);

    }

    public WebApiSimException(int code, String message) {

        this(code, message, null);

    }

    public WebApiSimException(int code, String message, Throwable throwable) {

        super(message, throwable);

        this.code = code;

    }

    public int getCode() {

        return code;

    }
}
