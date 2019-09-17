package eu.cise.dispatcher;

import java.io.Serializable;

/**
 * Dispatching a message will produce a result (success or failure) with a
 * related message.
 * <br>
 * This class is mostly an immutable  value object that holds the resulting
 * status.
 */
@SuppressWarnings("unused")
public class DispatchResult implements Serializable {

    // life, the universe and the everything.
    private static final long serialVersionUID = 42L;

    // internal state
    private final boolean ok;
    private final String result;

    /**
     * The constructor allow to create an immutable object that will contain
     * the status (success or failure) using a boolean and a string containing
     * a message
     *
     * @param ok     a boolean indicating if it's successful or not
     * @param result a result message describing the status
     */
    public DispatchResult(boolean ok, String result) {
        this.ok = ok;
        this.result = result;
    }

    /**
     * Helper method to create a default success result
     *
     * @return a new instance of successful DispatchResult
     */
    public static DispatchResult success() {
        return new DispatchResult(true, "SUCCESS");
    }

    /**
     * Helper method to create a default failure result
     *
     * @return a new instance of failed DispatchResult
     */
    public static DispatchResult failure() {
        return new DispatchResult(false, "FAILURE");
    }

    /**
     * @return the status of the result: true is successful while false is
     * failed
     */
    public boolean isOK() {
        return ok;
    }

    /**
     * @return an additional message of the results that in the failure could
     * contain a description of the error
     */
    public String getResult() {
        return result;
    }

    // To compare value objects we need equals and hashcode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DispatchResult that = (DispatchResult) o;

        if (ok != that.ok) return false;
        return result != null ? result.equals(that.result) : that.result == null;
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
