package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Message;

public class DefaultMessageProcessor implements MessageProcessor {

    private SignatureService signatureService;
    private EmuConfig config;

    public DefaultMessageProcessor(SignatureService signatureService, EmuConfig config) {
        this.signatureService = signatureService;
        this.config = config;
    }

    @Override
    public Message preview(Message message, SendParam param) {
        EmulatorEngine emulatorEngine = new DefaultEmulatorEngine(signatureService, config);
        return emulatorEngine.prepare(message, param);
    }
}
