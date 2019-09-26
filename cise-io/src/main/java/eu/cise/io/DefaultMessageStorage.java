package eu.cise.io;

public class DefaultMessageStorage implements MessageStorage {
    private Object object;

    public DefaultMessageStorage() {
    }

    @Override
    public void store(Object object) {
        this.object = object;
    }

    @Override
    public Object read() {
        return object;
    }

    protected boolean isObjectNull() {
        return object == null;
    }
}
