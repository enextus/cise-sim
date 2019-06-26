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
    }


    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {
        XmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();
        SimConfig config = createConfig();
        simApp = new SimApp(new SourceStreamProcessor(),
                new Sender(),
                new SimLogger.Slf4j(),
                config, xmlMapper, validator);

        Logger logger = LoggerFactory.getLogger("eu.cise.emulator.app.cli");
        if ((namespace.getString("send").equals(""))) {
            logger.debug("to send -> " + namespace.getString("send"));
        }

        simApp.run();
        String servicefile = "";

        if (!(namespace.getString("send").equals(""))) {
            servicefile = namespace.getString("service");

        }

        try {
            simApp.sendEvent(servicefile, "");
        } catch (Throwable e) {
            logger.error("An error occurred:\n\n" + e.getMessage() + "\n");

            if (simApp.isDebug = true)
                logger.error(e.getStackTrace().toString());
        }
    }

    /**
     * Retrieve the configuration object.
     *
     * @return a SimConfig object.
     */
    private static SimConfig createConfig() {
        return ConfigFactory.create(SimConfig.class);
    }

}
