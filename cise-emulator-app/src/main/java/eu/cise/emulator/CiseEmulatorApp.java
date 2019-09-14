package eu.cise.emulator;

import eu.cise.emulator.;
import eu.cise.emulator.Configuration;
import eu.eucise.xml.DefaultXmlMapper;
import eu.europa.ec.jrc.marex.core.Executor;
import eu.europa.ec.jrc.marex.core.InboundService;
import eu.europa.ec.jrc.marex.core.sub.MessageValidator;
import eu.europa.ec.jrc.marex.core.sub.Sender;
import eu.europa.ec.jrc.marex.core.sub.SourceStreamProcessor;
import eu.europa.ec.jrc.marex.util.SimLogger;

public class CiseEmulatorApp {
    private Configuration configuration;


    public static void main(final String[] args) throws Exception {
         new CiseEmulatorApi().run(args);
    }




    public void init() {

        DefaultXmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();
        SourceStreamProcessor sourceStreamProcessor = new SourceStreamProcessor();

    }
}
