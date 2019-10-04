package eu.cise.emulator.exceptions;

import java.io.IOException;

public class IOLoaderDirectoryNotFoundException extends IOLoaderException {

    public IOLoaderDirectoryNotFoundException(IOException e) {
        super("as directory is not found", e);
    }
}
