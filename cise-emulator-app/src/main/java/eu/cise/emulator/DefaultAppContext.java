package eu.cise.emulator;

import eu.cise.signature.SignatureService;
import eu.cise.signature.SignatureServiceBuilder;
import org.aeonbits.owner.ConfigFactory;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

public class DefaultAppContext implements AppContext {

    private final EmuConfig emuConfig;

    DefaultAppContext() {
        this.emuConfig = ConfigFactory.create(EmuConfig.class);
    }

    @Override
    public MessageProcessor makeMessageProcessor() {
        return new DefaultMessageProcessor(new DefaultEmulatorEngine(makeSignatureService(), this.emuConfig), makeSignatureService(), this.emuConfig);
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
