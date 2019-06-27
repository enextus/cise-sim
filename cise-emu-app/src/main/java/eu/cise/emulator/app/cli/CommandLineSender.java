package eu.cise.emulator.app.cli;

import eu.cise.emulator.app.SimApp;
import eu.cise.emulator.app.candidate.Sender;
import eu.cise.emulator.app.candidate.SourceStreamProcessor;
import eu.cise.emulator.app.context.SimConfig;
import eu.cise.emulator.app.util.SimLogger;
import eu.cise.emulator.httptransport.Validation.MessageValidator;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.cli.Command;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineSender extends Command {
    private SimApp simApp;

    public CommandLineSender() {
        super("sender", "sending from console");
    }

    @Override
    public void configure(Subparser subparser) {
        subparser.addArgument("-s", "--send")
                .dest("send")
                .type(String.class)
                .required(true)
                .help("add templateXml name to send configured destination ");

        subparser.addArgument("-c", "--config")
                .dest("config")
                .type(String.class)
                .required(true)
                .help("add specific config (to change id and destination)");
    }


    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {
        XmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();
        SimConfig config;
        Logger logger = LoggerFactory.getLogger("eu.cise.emulator.app.cli");

        String aConfigParam = namespace.getString("config");
        if (!(aConfigParam.equals(""))) {
            config = SimConfig.createMyConfig(aConfigParam);
            logger.debug("to send -> " + namespace.getString("send"));
        } else {
            config = ConfigFactory.create(SimConfig.class);
        }

        String servicefile = namespace.getString("send");
        if ((servicefile.equals(""))) {
            System.out.println("ERROR:  add a template xml file to the command line expression with the \"-s\" option");
            return;
        }

        simApp = new SimApp(new SourceStreamProcessor(),
                new Sender(),
                new SimLogger.Slf4j(),
                config, xmlMapper, validator);
        simApp.run();

        try {
            simApp.sendEvent(servicefile, "");
        } catch (Throwable e) {
            logger.error("error occurred:\n\n" + e.getMessage() + "\n");

        }

    }


}
