package eu.cise.emulator.deprecated.web.httptransport;

import java.io.Serializable;

/**
 * Result of the dispatching of a message
 */
public class DispatchResult implements Serializable {

    private static final long serialVersionUID = 42L;

    private final boolean ok;
    private final String result;

    public DispatchResult(boolean ok, String result) {
        this.ok = ok;
        this.result = result;
    }

    public static DispatchResult success() {
        return new DispatchResult(true, "SUCCESS");
    }

    public static DispatchResult failure() {
        return new DispatchResult(false, "FAILURE");
    }

    public boolean isOK() {
        return ok;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DispatchResult that = (DispatchResult) o;

        return ok == that.ok && (result != null ? result.equals(that.result) : that.result == null);
    }

    @Override
    public int hashCode() {
        int result1 = (ok ? 1 : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "DispatchResult{" +
                "ok=" + ok +
                ", result='" + result + '\'' +
                '}';
    }

}
