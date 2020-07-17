package eu.cise.sim.api.helpers.result;

@FunctionalInterface
public interface CheckedSupplier<V, E extends Throwable> {
    V get() throws E;
}
