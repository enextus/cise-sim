package eu.cise.emulator.io;

public class DefaultMessageStorage implements MessageStorage {
    private Object object;

    @Override
    public void store(Object object) {
        this.object = object;
    }

    @Override
    public Object read() {
        return object;
    }

    boolean isObjectNull() {
        return object == null;
    }
}
