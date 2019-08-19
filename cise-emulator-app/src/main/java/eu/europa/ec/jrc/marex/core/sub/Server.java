package eu.europa.ec.jrc.marex.core.sub;


public interface Server {

    static Server getInstance() {
        return null;
    }

    boolean isStarted();


}
