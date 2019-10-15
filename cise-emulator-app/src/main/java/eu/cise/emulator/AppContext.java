package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.XmlMapper;

public interface AppContext {

    MessageProcessor makeMessageProcessor();

    Dispatcher makeDispatcher();

    SignatureService makeSignatureService();

    MessageStorage makeMessageStorage();

    TemplateLoader makeTemplateLoader();

    XmlMapper getXmlMapper();

    EmuConfig makeEmuConfig();
}
