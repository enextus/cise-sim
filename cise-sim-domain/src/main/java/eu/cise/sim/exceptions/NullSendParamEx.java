package eu.cise.sim.exceptions;

public class NullSendParamEx extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "EmulatorEngine: the method with signature\n" +
            "<T extends Message> T prepare(T message, SendParam param);\n" +
            "has received null reference instead of a SendParam instance.";

    public NullSendParamEx() {
        super(EXCEPTION_MESSAGE);
    }
}
