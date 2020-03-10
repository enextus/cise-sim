package eu.cise.cli;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherFactory;
import eu.cise.emulator.DefaultEmulatorEngine;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.EmulatorEngine;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.aeonbits.owner.ConfigFactory;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

public class CliAppContext {

    private final EmuConfig emuConfig;
    private final XmlMapper xmlMapper;
    private final SignatureService signatureService;

    public CliAppContext() {
        this.emuConfig = ConfigFactory.create(EmuConfig.class);
        this.xmlMapper = new DefaultXmlMapper.NotValidating();
        this.signatureService = newSignatureService(xmlMapper)
                .withKeyStoreName(emuConfig.keyStoreFileName())
                .withKeyStorePassword(emuConfig.keyStorePassword())
                .withPrivateKeyAlias(emuConfig.privateKeyAlias())
                .withPrivateKeyPassword(emuConfig.privateKeyPassword())
                .build();
    }

    public EmuConfig getEmuConfig() {
        return this.emuConfig;
    }

    public EmulatorEngine makeEmulatorEngine() {
        return new DefaultEmulatorEngine(signatureService, makeDispatcher(), emuConfig);
    }

    public Dispatcher makeDispatcher() {
        DispatcherFactory dispatcherFactory = new DispatcherFactory();
        return dispatcherFactory
                .getDispatcher(this.emuConfig.destinationProtocol(), this.xmlMapper);
    }

    public MessageLoader makeMessageLoader() {
        return new MessageLoader(xmlMapper);
    }
}
