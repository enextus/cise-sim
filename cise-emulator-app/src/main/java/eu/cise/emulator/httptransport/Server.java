package eu.cise.emulator.httptransport;


public interface Server {

    static Server getInstance() {
        return null;
    }

    boolean isStarted();


}
