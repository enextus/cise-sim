package eu.cise.dispatcher.rest;

import java.io.Serializable;

/**
 * The class is a value object containing the information coming back from a RESTful request.
 * <ul>
 * <li>The code will contain the HTTP code of the response.</li>
 * <li>The isOK() method is a shortcut to understand if the request has ended
 * with an error or a successful response</li>
 * <li>The body will contain the full body of the response coming from
 * the server.</li>
 * <li>The message will contain a specific text message coming from the
 * response if any</li>
 * </ul>
 */
@SuppressWarnings("unused")
public class RestResult implements Serializable {

    // life, the universe and the everything.
    private static final long serialVersionUID = 42L;

    private final Integer code;
    private final boolean ok;
    private final String body;
    private final String message;

    /**
     * Creates a new value object with some specific information about the response.
     *
     * @param code    HTTP response code
     * @param body    HTTP response body
     * @param message HTTP response text message
     */
    public RestResult(Integer code, String body, String message) {
        this.code = code;
        this.ok = isOK(code);
        this.body = body;
        this.message = message;
    }

    /**
     * If the status code is a 2XX it interprets the response as a successful one.
     *
     * @param statusCode the status code to be interpreted.
     * @return true if the status code is 2XX and false otherwise.
     */
    public static boolean isOK(final int statusCode) {
        return statusCode / 100 == 2;
    }

    /**
     * @return the HTTP code coming from the response object.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @return the HTTP body coming from the response object.
     */
    public String getBody() {
        return body;
    }

    /**
     * @return true if is an HTTP 2XX response. False otherwise.
     */
    public boolean isOK() {
        return ok;
    }

    /**
     * @return HTTP message in case of text in the response.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RestResult that = (RestResult) o;

        if (ok != that.ok)
            return false;
        if (code != null ? !code.equals(that.code) : that.code != null)
            return false;
        if (body != null ? !body.equals(that.body) : that.body != null)
            return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (ok ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "rest-result{\n" +
            "  ok: " + ok +
            "  code: " + code + "\n" +
            "  message: " + message + "\n" +
            "  body: " + body + "\n" +
            "}";
    }
}
