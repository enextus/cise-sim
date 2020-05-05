package eu.cise.cli.exceptions;

public class FileLoaderEx extends RuntimeException {
    public FileLoaderEx(Exception ioe) {
        super(ioe);
    }
}
