package eu.cise.sim.api;

import java.io.Serializable;
import java.util.Objects;

public class APIError implements Serializable {

    private static final long serialVersionUID = 42L;

    private final String error;

    public APIError() {
        this.error = "Generic error";
    }

    public APIError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIError apiError = (APIError) o;
        return error.equals(apiError.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }
}
