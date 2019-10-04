package eu.cise.emulator.api;

public class IOLoaderException extends RuntimeException {

    public IOLoaderException() {
    super("IO error while loading template");
    }
}
