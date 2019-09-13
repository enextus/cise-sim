package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public interface EmulatorEngine {

    Acknowledgement send(Message message);

    Message prepare(Message message, SendParam param);
}
