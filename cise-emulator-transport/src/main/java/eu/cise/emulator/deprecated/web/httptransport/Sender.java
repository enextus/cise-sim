package eu.cise.emulator.deprecated.web.httptransport;

public interface Sender {

    static Sender getInstance() {
        return null;
    }

    boolean isReady();

}
