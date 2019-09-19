package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.emulator.api.CiseEmulatorAPI;
import eu.cise.signature.SignatureService;

public class CiseEmulatorApp implements Runnable {

    private final EmuConfig emuConfig;
    private final MessageProcessor messageProcessor;
    private final EmulatorEngine emulatorEngine;
    private final Dispatcher dispatcher;
    private final SignatureService signatureService;
    private final CiseEmulatorAPI emulatorApi;

    public CiseEmulatorApp(EmuConfig emuConfig,
                           MessageProcessor messageProcessor,
                           EmulatorEngine emulatorEngine,
                           Dispatcher dispatcher,
                           SignatureService signatureService,
                           CiseEmulatorAPI emulatorApi
                           ) {

        this.emuConfig = emuConfig;
        this.messageProcessor = messageProcessor;
        this.emulatorEngine = emulatorEngine;
        this.dispatcher = dispatcher;
        this.signatureService = signatureService;
        this.emulatorApi = emulatorApi;

    }

    @Override
    public void run() {
        //String[] command = {"server", "config.yml"};
    }

}
