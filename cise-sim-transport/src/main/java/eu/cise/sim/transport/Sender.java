package eu.cise.sim.transport;

public interface Sender {

    static Sender getInstance(){return null;}

    boolean isReady();

}
