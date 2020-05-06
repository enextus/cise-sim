package eu.cise.sim.app;

import eu.cise.dispatcher.DispatcherFactory;
import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.DefaultMessageProcessor;
import eu.cise.sim.engine.DefaultSimEngine;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.io.*;
import eu.cise.sim.templates.DefaultTemplateLoader;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.aeonbits.owner.ConfigFactory;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;


public class DefaultAppContext implements AppContext {

    private final SimConfig simConfig;
    private final XmlMapper xmlMapper;
    private final XmlMapper prettyNotValidatingXmlMapper;


    public DefaultAppContext() {
        this.simConfig = ConfigFactory.create(SimConfig.class);
        this.xmlMapper = new DefaultXmlMapper.NotValidating();

        // TODO GK testing if we have any issue
        this.prettyNotValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
    }

    @Override
    public MessageProcessor makeMessageProcessor() {
        return new DefaultMessageProcessor(makeSimEngine());
    }

    @Override
    public MessageProcessor makeMessageProcessor(MessagePersistence messagePersistence) {
        return new DefaultMessageProcessor(makeSimEngine(), messagePersistence);
    }

    private DefaultSimEngine makeSimEngine() {
        return new DefaultSimEngine(makeSignatureService(), makeDispatcher(), this.simConfig);
    }

    @Override
    public Dispatcher makeDispatcher() {
        DispatcherFactory dispatcherFactory = new DispatcherFactory();
        //*correlation:Disp-Sign where P= pretty V=Valid p=nonpretty or v=nonvalid: signature.fail:Pv-Pv,Pv-pv,pv-Pv  and sax.fail: PV-PV,pV-pV success:pv-pv
        return dispatcherFactory.getDispatcher(this.simConfig.destinationProtocol(), this.xmlMapper);

    }

    @Override
    public SignatureService makeSignatureService() {
        //*correlation:Disp-Sign where P= pretty V=Valid p=nonpretty or v=nonvalid: signature.fail:Pv-Pv,Pv-pv,pv-Pv  and sax.fail: PV-PV,pV-pV success:pv-pv
        return newSignatureService(this.xmlMapper)
                .withKeyStoreName(this.simConfig.keyStoreFileName())
                .withKeyStorePassword(this.simConfig.keyStorePassword())
                .withPrivateKeyAlias(this.simConfig.privateKeyAlias())
                .withPrivateKeyPassword(this.simConfig.privateKeyPassword())
                .build();
    }

    @Override
    public MessageStorage<Object> makeMessageStorage() {
        return new DefaultMessageStorage();
    }

    @Override
    public HistoryPersistence makeHistoryMessageStorage() {
        return new DummyHistoryPersistence();
    }

    @Override
    public TemplateLoader makeTemplateLoader() {
        return new DefaultTemplateLoader(simConfig);
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
    public SimConfig makeEmuConfig() {
        return simConfig;
    }
}
