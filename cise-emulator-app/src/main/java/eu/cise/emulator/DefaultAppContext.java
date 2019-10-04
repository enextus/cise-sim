package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.RestDispatcher;
import eu.cise.emulator.templates.DefaultTemplateLoader;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.emulator.api.helpers.DropWizardServerBuilder;
import eu.cise.emulator.api.EmulatorApp;
import eu.cise.emulator.io.DefaultMessageStorage;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
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
    public EmulatorApp makeEmulatorApi(MessageProcessor messageProcessor, MessageStorage messageStorage, TemplateLoader templateLoader, XmlMapper xmlMapper) {
        EmulatorApp server = null;
        try {
            String configFile = (this.emuConfig.webapiConfig());
            server = DropWizardServerBuilder
                .createServer(configFile, EmulatorApp.class, messageProcessor, messageStorage, emuConfig, templateLoader, xmlMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return server;
    }

    @Override
    public MessageStorage makeMessageStorage() {
        return new DefaultMessageStorage();
    }

    @Override
    public TemplateLoader makeTemplateLoader(XmlMapper xmlMapper) {
        return new DefaultTemplateLoader(xmlMapper);
    }

    @Override
    public XmlMapper makeXmlMapper() {
        return new DefaultXmlMapper();
    }

}
