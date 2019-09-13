package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public class DefaultEmulatorEngine implements EmulatorEngine {

    @Override
    public Message prepare(Message message, SendParam param) {
        return null;
    }

    @Override
    public Acknowledgement send(Message message) {
        return null;
    }

}
