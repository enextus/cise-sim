package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.RestDispatcher;
import eu.cise.emulator.api.helpers.CiseDropWizardServerBuilder;
import eu.cise.emulator.api.CiseEmulatorAPI;
import eu.cise.io.DefaultMessageStorage;
import eu.cise.io.MessageStorage;
import eu.cise.signature.SignatureService;
import org.aeonbits.owner.ConfigFactory;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

public class DefaultAppContext implements AppContext {

    private final EmuConfig emuConfig;

    DefaultAppContext() {
        this.emuConfig = ConfigFactory.create(EmuConfig.class);
    }

    @Override
    public MessageProcessor makeMessageProcessor() {
        return new DefaultMessageProcessor(makeEmulatorEngine());
    }

    //TODO - implement makeDispatcher
    @Override
    public DefaultEmulatorEngine makeEmulatorEngine() {
        return new DefaultEmulatorEngine(makeSignatureService(), makeDispatcher(), this.emuConfig);
    }

    @Override
    public Dispatcher makeDispatcher() {
        return new RestDispatcher();
    }

    @Override
    public SignatureService makeSignatureService() {
        return newSignatureService()
                .withKeyStoreName(this.emuConfig.keyStoreFileName())
                .withKeyStorePassword(this.emuConfig.keyStorePassword())
                .withPrivateKeyAlias(this.emuConfig.privateKeyAlias())
                .withPrivateKeyPassword(this.emuConfig.privateKeyPassword())
                .build();
    }

    @Override
    public CiseEmulatorAPI makeEmulatorApi(MessageProcessor messageProcessor, MessageStorage messageStorage) {
        CiseEmulatorAPI server = null;
        try {
            String configFile = (this.emuConfig.webapiConfig());
            server = CiseDropWizardServerBuilder.createServer(configFile, CiseEmulatorAPI.class, messageProcessor, messageStorage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return server;
    }

    @Override
    public MessageStorage makeMessageStorage() {
        return new DefaultMessageStorage();
    }
}
