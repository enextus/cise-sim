package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.emulator.api.CiseEmulatorApi;
import eu.cise.signature.SignatureService;

public interface AppContext {

    MessageProcessor makeMessageProcessor();

    DefaultEmulatorEngine makeEmulatorEngine();

    Dispatcher makeDispatcher();

    SignatureService makeSignatureService();

    CiseEmulatorApi makeEmulatorApi();
}
