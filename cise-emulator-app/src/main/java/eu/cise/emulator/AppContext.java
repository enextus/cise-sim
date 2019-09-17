package eu.cise.emulator;

import eu.cise.signature.SignatureService;

public interface AppContext {

    MessageProcessor makeMessageProcessor();

    DefaultEmulatorEngine makeEmulatorEngine();

    SignatureService makeSignatureService();
}
