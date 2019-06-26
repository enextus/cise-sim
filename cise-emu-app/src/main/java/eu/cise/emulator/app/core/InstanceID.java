package eu.cise.emulator.app.core;


import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


public class InstanceID {
    private final Long numid=  new AtomicLong().get();
    private final String name;

    public InstanceID(String name) {
        this.name = name;
    }

    public String render(Optional<String> name) {
        return (":"+this.name+":"+this.numid);
    }

    public Long getNumId() {
        return this.numid;
    }

    public String getName() {return this.name;}
}
