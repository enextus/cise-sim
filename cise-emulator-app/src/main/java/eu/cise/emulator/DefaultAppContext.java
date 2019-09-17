package eu.cise.emulator;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

import eu.cise.signature.SignatureService;
import org.aeonbits.owner.ConfigFactory;

public class DefaultAppContext implements AppContext {

    private final EmuConfig emuConfig;

    DefaultAppContext() {
        this.emuConfig = ConfigFactory.create(EmuConfig.class);
    }

    @Override
    public MessageProcessor makeMessageProcessor() {
        return new DefaultMessageProcessor(
            new DefaultEmulatorEngine(makeSignatureService(), this.emuConfig),
            makeSignatureService(), this.emuConfig);
    }

    private SignatureService makeSignatureService() {
        return newSignatureService()
            .withKeyStoreName("adaptor.jks")
            .withKeyStorePassword("eucise")
            .withPrivateKeyAlias("sim1-node01.node01.eucise.fr")
            .withPrivateKeyPassword("eucise")
            .build();
    }
}
