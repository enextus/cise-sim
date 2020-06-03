package eu.cise.sim.app;

import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.XmlMapper;

public interface AppContext {

    MessageProcessor makeMessageProcessor();
    MessageProcessor makeMessageProcessor(MessagePersistence messagePersistence);

    Dispatcher makeDispatcher();

    SignatureService makeSignatureService();

    MessageStorage<Object> makeMessageStorage();

    TemplateLoader makeTemplateLoader();

    XmlMapper getXmlMapper();

    XmlMapper getPrettyNotValidatingXmlMapper();

    SimConfig makeEmuConfig();

    String getRepoDir();

    int getGuiMaxThMsgs();
}
