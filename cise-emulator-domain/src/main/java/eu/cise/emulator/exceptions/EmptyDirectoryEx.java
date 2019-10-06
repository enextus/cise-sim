package eu.cise.emulator.exceptions;

import java.io.IOException;

public class EmptyDirectoryEx extends LoaderEx {
    public EmptyDirectoryEx(IOException e) {
        super("as directory is empty", e);
    }
}
