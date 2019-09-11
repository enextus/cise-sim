package eu.cise.emulator.app.core;


import java.util.concurrent.atomic.AtomicLong;


public class InstanceID {
    public static final String ACTOR_WEB_CLIENT = "WClient";
    public static final String ACTOR_WEB_SERVER = "WServer";
    private final Long numid = new AtomicLong().get();
    private final String version;
    private final String name;

    public InstanceID(String name, String version) {
        this.version = version;
        this.name = name;
    }

    public Long getNumId() {
        return this.numid;
    }

    public String getVersion() {
        return this.version;
    }

    public String getName() {

        return this.name;
    }
}
