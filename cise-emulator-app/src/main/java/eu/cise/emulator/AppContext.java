package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.signature.SignatureService;

public interface AppContext {

    MessageProcessor makeMessageProcessor();

    DefaultEmulatorEngine makeEmulatorEngine();

    Dispatcher makeDispatcher();

    SignatureService makeSignatureService();
}
