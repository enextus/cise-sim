package eu.cise.sim.api.helpers.result;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class Result<V, E extends Throwable> {
    private final V value;
    private final E error;

    private Result(V value, E error) {
        this.value = value;
        this.error = error;
    }

    public static <V, E extends Throwable> Result<V, E> failure(E error) {
        return new Result<>(null, Objects.requireNonNull(error));
    }

    public static <V, E extends Throwable> Result<V, E> success(V value) {
        return new Result<>(Objects.requireNonNull(value), null);
    }

    public <T> Result<T, E> map(Function<? super V, ? extends T> mapper) {
        return Optional.ofNullable(error)
                .map(e -> Result.<T, E>failure(e))
                .orElseGet(() -> Result.success(mapper.apply(value)));
    }

    public V orElseThrow() throws E {
        return Optional.ofNullable(value).orElseThrow(() -> error);
    }


    public static <V, E extends Throwable> Result<V, E> attempt(CheckedSupplier<? extends V, ? extends E> p) {
        try {
            return Result.success(p.get());
        } catch (Throwable e) {
            @SuppressWarnings("unchecked")
            E err = (E) e;
            return Result.failure(err);
        }
    }

    public static void main(String[] args) throws IOException {

        Result<Integer, IOException> result = Result.failure(new IOException("Ciccio"));

        System.out.println(result.orElseThrow());
    }
}