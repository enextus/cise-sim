package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.RestDispatcher;
import eu.cise.emulator.io.DefaultMessageStorage;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.DefaultTemplateLoader;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.aeonbits.owner.ConfigFactory;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

public class DefaultAppContext implements AppContext {

    private final EmuConfig emuConfig;
    private final XmlMapper xmlMapper;
    private final XmlMapper prettyNotValidatingXmlMapper;



    public DefaultAppContext() {
        this.emuConfig = ConfigFactory.create(EmuConfig.class);
        this.xmlMapper = new DefaultXmlMapper.Pretty();
        this.prettyNotValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
    }

    @Override
    public MessageProcessor makeMessageProcessor() {
        return new DefaultMessageProcessor(makeEmulatorEngine());
    }

    private DefaultEmulatorEngine makeEmulatorEngine() {
        return new DefaultEmulatorEngine(makeSignatureService(), makeDispatcher(), this.emuConfig, this.prettyNotValidatingXmlMapper);
    }

    @Override
    public Dispatcher makeDispatcher() {
        return new RestDispatcher(prettyNotValidatingXmlMapper);
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
    public MessageStorage makeMessageStorage() {
        return new DefaultMessageStorage();
    }

    @Override
    public TemplateLoader makeTemplateLoader() {
        return new DefaultTemplateLoader(emuConfig);
    }

    @Override
    public XmlMapper getXmlMapper() {
        return xmlMapper;
    }

    @Override
    public XmlMapper getPrettyNotValidatingXmlMapper() {
        return prettyNotValidatingXmlMapper;
    }
    @Override
    public EmuConfig makeEmuConfig() {
        return emuConfig;
    }
}
