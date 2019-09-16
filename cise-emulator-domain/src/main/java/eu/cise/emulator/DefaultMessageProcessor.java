package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

public class DefaultMessageProcessor implements MessageProcessor {

    private final EmulatorEngine emulatorEngine;
    private final SignatureService signatureService;
    private final EmuConfig config;

    public DefaultMessageProcessor(EmulatorEngine emulatorEngine, SignatureService signatureService, EmuConfig config) {
        this.signatureService = signatureService;
        this.config = config;
        this.emulatorEngine = emulatorEngine;
    }

    @Override
    public Message preview(Message message, SendParam param) {
        return emulatorEngine.prepare(message, param);
    }

    @Override
    public Acknowledgement send(Message message, SendParam param) {
        Message preparedMessage = emulatorEngine.prepare(message, param);
        Acknowledgement acknowledgement = emulatorEngine.send(preparedMessage, param);
        return acknowledgement;
    }
}
