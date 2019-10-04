package eu.cise.emulator.api;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Wraps an {@link UncheckedIOException}.
 *
 * @since   1.8
 */
public class IOLoaderException extends UncheckedIOException  {

    public IOLoaderException(String message, IOException e) {
        super("IO error while loading template " + message,  e);
    }

    public IOLoaderException(String message, eu.cise.emulator.exceptions.IOLoaderException e) {
        super("IO error while loading template " + message,  new IOException(e.getMessage()));
    }


}

