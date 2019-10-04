package eu.cise.emulator.exceptions;

import java.io.IOException;

//wrapper of io exception like java.nio.file.NoSuchFileException
public class IOLoaderDirectoryNotFoundException extends IOLoaderException {

    public IOLoaderDirectoryNotFoundException(IOException e) {
        super("as directory is not found", e);
    }
}
