package eu.cise.sim.dropw.context;

import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.dispatcher.DispatcherFactory;
import eu.cise.signature.SignatureService;
import eu.cise.sim.api.DefaultMessageAPI;
import eu.cise.sim.api.DefaultTemplateAPI;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.TemplateAPI;
import eu.cise.sim.api.history.FileMessageService;
import eu.cise.sim.api.history.ThreadMessageService;
import eu.cise.sim.config.ProxyManager;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.dropw.soapresources.CISEMessageServiceSoapImplDefault;
import eu.cise.sim.engine.DefaultMessageProcessor;
import eu.cise.sim.engine.DefaultSimEngine;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.templates.DefaultTemplateLoader;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

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

        checkConfig();
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

    public String makeProxy() {
        String result = "PROXY: no proxy configured";
        String host = simConfig.proxyHost();
        String port = simConfig.proxyPort();

        if (!StringUtils.isEmpty(host) || !StringUtils.isEmpty(port)) {

            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port);
            result = "PROXY: activated on host[" + host + "] port[" + port + "]";
        }
        return result;
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
    public ThreadMessageService getThreadMessageService() {
        return  new FileMessageService(getPrettyNotValidatingXmlMapper(),
                                       getRepoDir(),
                                       getGuiMaxThMsgs());

    }

    @Override
    public MessageAPI getMessageAPI(MessagePersistence messagePersistence) {
        return new DefaultMessageAPI(
                makeMessageProcessor(messagePersistence),
                makeTemplateLoader(),
                getXmlMapper(),
                getPrettyNotValidatingXmlMapper());
    }

    @Override
    public TemplateAPI getTemplateAPI() {
        return new DefaultTemplateAPI(
                    makeMessageProcessor(),
                    makeTemplateLoader(),
                    getXmlMapper(),
                    getPrettyNotValidatingXmlMapper());
    }

    @Override
    public CISEMessageServiceSoapImpl getServiceSoap(MessageAPI messageAPI) {
        return new CISEMessageServiceSoapImplDefault(
                messageAPI,
                getPrettyNotValidatingXmlMapper());
    }

    @Override
    public SimConfig makeEmuConfig() {
        return simConfig;
    }

    @Override
    public String getRepoDir() {
        return simConfig.messageHistoryDir();
    }

    @Override
    public int getGuiMaxThMsgs() {
        return simConfig.guiMaxThMsgs();
    }

    @Override
    public ProxyManager makeProxyManager() {
        return new DefaultProxyManager(simConfig.proxyHost(), simConfig.proxyPort());
    }

    public void checkConfig() {

        if (StringUtils.isEmpty(simConfig.simulatorName())) {
            throw new ConfigurationException("Simulator name not configured");
        }

        if (StringUtils.isEmpty(simConfig.appVersion())) {
            throw new ConfigurationException("Simulator version not configured");
        }

        if (StringUtils.isEmpty(simConfig.destinationUrl())) {
            throw new ConfigurationException("Destination Url not configured");
        } else {
            try {
                new URL(simConfig.destinationUrl());
            } catch (MalformedURLException e) {
                throw new ConfigurationException("Destination Url malformed " + e.getMessage());
            }
        }

        if (StringUtils.isEmpty(simConfig.messageTemplateDir())) {
            throw new ConfigurationException("Message template dir not configured");
        } else {
            try {
                Paths.get(simConfig.messageTemplateDir());
            } catch (InvalidPathException e) {
                throw new ConfigurationException("Message template dir malformed " + e.getMessage());
            }
        }

        if (StringUtils.isEmpty(simConfig.messageHistoryDir())) {
            throw new ConfigurationException("Message history dir not configured");
        } else {
            try {
                Paths.get(simConfig.messageHistoryDir());
            } catch (InvalidPathException e) {
                throw new ConfigurationException("Message history dir malformed " + e.getMessage());
            }
        }

        try {
            if (simConfig.guiMaxThMsgs() <= 0) {
                throw new ConfigurationException("Maximum number of Threads value is negative");
            }
        } catch (RuntimeException e) {
            throw new ConfigurationException("Maximum number of Threads not configured");
        }

        try {
            simConfig.destinationProtocol();
        } catch (RuntimeException e) {
            throw new ConfigurationException("Wrong destination protocol " + e.getMessage());
        }

        // This will check the signature configuration
        try {
            makeSignatureService();
        } catch (RuntimeException e) {
            String errMsg = e.getMessage();
            if (StringUtils.isEmpty(errMsg) && e.getCause() != null) {
                Throwable t = e.getCause();
                errMsg = t.getMessage();
                if (StringUtils.isEmpty(errMsg) && t.getCause() != null) {
                    t = t.getCause();
                    errMsg = t.getMessage();
                    if (StringUtils.isEmpty(errMsg) && t.getCause() != null) {
                        t = t.getCause();
                        errMsg = t.getMessage();
                    }
                }
            }
            throw new ConfigurationException("Wrong signature configuration : " + errMsg);
        }

        // Check proxy
        DefaultProxyManager.checkConfiguration(simConfig.proxyHost(), simConfig.proxyPort());

    }
}
