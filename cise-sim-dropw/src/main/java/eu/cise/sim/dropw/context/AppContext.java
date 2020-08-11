package eu.cise.sim.dropw.context;

import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.signature.SignatureService;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.TemplateAPI;
import eu.cise.sim.api.history.ThreadMessageService;
import eu.cise.sim.config.ProxyManager;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.XmlMapper;

public interface AppContext {

    MessageProcessor makeMessageProcessor();
    MessageProcessor makeMessageProcessor(MessagePersistence messagePersistence);

    Dispatcher makeDispatcher();

    SignatureService makeSignatureService();

    ProxyManager makeProxyManager();

    TemplateLoader makeTemplateLoader();

    XmlMapper getXmlMapper();

    XmlMapper getPrettyNotValidatingXmlMapper();

    ThreadMessageService getThreadMessageService();

    MessageAPI getMessageAPI(MessagePersistence messagePersistence);

    TemplateAPI getTemplateAPI();

    CISEMessageServiceSoapImpl getServiceSoap(MessageAPI messageAPI);

    SimConfig makeEmuConfig();

    String getRepoDir();

    int getGuiMaxThMsgs();

}
