package eu.cise.emulator.exceptions;

import java.io.IOException;
import java.io.UncheckedIOException;

public class IOLoaderException extends UncheckedIOException {
    public IOLoaderException() {
        super("IO error while loading template", new IOException());
    }

    public IOLoaderException(IOException e) {
        super("IO error while loading template : unknow ", e);
    }

    public IOLoaderException(String message, IOException e) {
        super("IO error while loading template : " + message, e);
    }
}

