package eu.cise.emulator;

import eu.cise.dispatcher.Dispatcher;
import eu.cise.emulator.api.EmulatorApp;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.XmlMapper;

public interface AppContext {

    MessageProcessor makeMessageProcessor();

    DefaultEmulatorEngine makeEmulatorEngine();

    Dispatcher makeDispatcher();

    SignatureService makeSignatureService();

    EmulatorApp makeEmulatorApi(MessageProcessor messageProcessor, MessageStorage messageStorage, TemplateLoader templateLoader, XmlMapper xmlMapper);

    MessageStorage makeMessageStorage();

    TemplateLoader makeTemplateLoader();

    XmlMapper makeXmlMapper();
}
