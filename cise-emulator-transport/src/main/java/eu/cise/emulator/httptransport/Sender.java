package eu.cise.emulator.httptransport;

public interface Sender {

    static Sender getInstance(){return null;}

    boolean isReady();

}
