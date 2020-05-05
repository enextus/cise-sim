package eu.cise.sim.engine;

import eu.cise.servicemodel.v1.message.Acknowledgement;

import java.io.Serializable;
import java.util.Objects;

/**
 * Dispatching a message will produce a result (success or failure) with a
 * related message.
 * <br>
 * This class is mostly an immutable  value object that holds the resulting
 * status.
 */
@SuppressWarnings("unused")
public class DispatchResult  implements Serializable {

    // life, the universe and the everything.
    private static final long serialVersionUID = 1609530741412720565L;

    // internal state
    private final boolean ok;
    private final Acknowledgement result;

    /**
     * The constructor allow to create an immutable object that will contain
     * the status (success or failure) using a boolean and a string containing
     * a message
     *
     * @param ok     a boolean indicating if it's successful or not
     * @param result a result message describing the status
     */
    public DispatchResult(boolean ok, Acknowledgement result) {
        this.ok = ok;
        this.result = result;
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
    public Acknowledgement getResult() {
        return result;
    }

    // To compare value objects we need equals and hashcode methods
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DispatchResult that = (DispatchResult) o;

        if (ok != that.ok) return false;
        return Objects.equals(result, that.result);
    }

    public int hashCode() {
        int result1 = (ok ? 1 : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    public String toString() {
        return "DispatchResult{" +
                "ok=" + ok +
                ", result='" + result + '\'' +
                '}';
    }

}
