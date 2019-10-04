package eu.cise.emulator.exceptions;

import java.io.IOException;

public class IOLoaderDirectoryEmptyException extends IOLoaderException {

    public IOLoaderDirectoryEmptyException(IOException e) {
        super("as directory is empty", e);
    }
}
