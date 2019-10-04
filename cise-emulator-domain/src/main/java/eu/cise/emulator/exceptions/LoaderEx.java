package eu.cise.emulator.exceptions;

import java.io.IOException;
import java.io.UncheckedIOException;

public class LoaderEx extends UncheckedIOException {
    public LoaderEx() {
        super("IO error while loading template", new IOException());
    }

    public LoaderEx(IOException e) {
        super("IO error while loading template : unknow ", e);
    }

    public LoaderEx(String message, IOException e) {
        super("IO error while loading template : " + message, e);
    }
}

