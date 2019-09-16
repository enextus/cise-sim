package eu.cise.emulator;
// TODO-GK modified by GK to make it compile
//import eu.cise.emulator.Configuration;

import eu.eucise.xml.DefaultXmlMapper;
import eu.europa.ec.jrc.marex.core.sub.MessageValidator;
import eu.europa.ec.jrc.marex.core.sub.SourceStreamProcessor;


public class CiseEmulatorApp {
// TODO-GK modified by GK to make it compile
    //    private Configuration configuration;


    public static void main(final String[] args) throws Exception {
// TODO-GK modified by GK to make it compile
        //        new CiseEmulatorApi().run(args);
    }


    public void init() {

        DefaultXmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();
        SourceStreamProcessor sourceStreamProcessor = new SourceStreamProcessor();

    }
}
