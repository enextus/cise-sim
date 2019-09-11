package eu.cise.emulator.app.transport;

import java.io.Serializable;

public class RestResult implements Serializable {

    private static final long serialVersionUID = 42L;

    private final Integer code;
    private final String body;
    private final String message;
    private final boolean ok;

    public RestResult(Integer code, String body, String message) {
        this.code = code;
        this.ok = isOK(code);
        this.body = body;
        this.message = message;
    }

    public static boolean isOK(final int statusCode) {
        return statusCode / 100 == 2;
    }

    public Integer getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

    public boolean isOK() {
        return ok;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestResult that = (RestResult) o;

        if (ok != that.ok) return false;
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
        return "RestResult{" +
                "code=" + code +
                ", body='" + body + '\'' +
                ", message='" + message + '\'' +
                ", ok=" + ok +
                '}';
    }
}
