package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.RestDispatcher;
import eu.cise.emulator.api.CiseDropWizardServerBuilder;
import eu.cise.emulator.api.CiseEmulatorAPI;
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
                .withKeyStoreName("adaptor.jks")
                .withKeyStorePassword("eucise")
                .withPrivateKeyAlias("sim1-node01.node01.eucise.fr")
                .withPrivateKeyPassword("eucise")
                .build();
    }

    @Override
    public CiseEmulatorAPI makeEmulatorApi(MessageProcessor messageProcessor) {
        CiseEmulatorAPI server = null;
        try {
            String configFile = (this.emuConfig.webapiConfig());
            server = CiseDropWizardServerBuilder.createServer(configFile, CiseEmulatorAPI.class, messageProcessor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return server;
    }
}
