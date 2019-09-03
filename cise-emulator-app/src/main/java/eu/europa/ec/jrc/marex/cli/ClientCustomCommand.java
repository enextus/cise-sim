package eu.europa.ec.jrc.marex.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import eu.europa.ec.jrc.marex.candidate.CiseEmulatorConfigurationException;
import eu.europa.ec.jrc.marex.client.RestResult;
import eu.europa.ec.jrc.marex.core.Executor;
import eu.europa.ec.jrc.marex.core.sub.MessageValidator;
import eu.europa.ec.jrc.marex.core.sub.Sender;
import eu.europa.ec.jrc.marex.core.sub.SourceStreamProcessor;
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration;
import eu.europa.ec.jrc.marex.emulator.AcknowledgementHelper;
import eu.europa.ec.jrc.marex.util.ConfigManager;
import eu.europa.ec.jrc.marex.util.InteractIOFile;
import eu.europa.ec.jrc.marex.util.SimLogger;
import io.dropwizard.cli.Command;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.ConfigurationFactoryFactory;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;

public class ClientCustomCommand extends Command {
    public static final String SUCCESS = "Success";
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCustomCommand.class);
    private static String MESSAGE_MODAL="SEND";
    private final AcknowledgementHelper acknowledgementHelper = new AcknowledgementHelper();

    public ClientCustomCommand() {
        super("sender", "sending from console");
    }


    @Override
    public void configure(Subparser subparser) {
        subparser.addArgument("-c", "--config")
                .dest("config")
                .type(String.class)
                .required(false)
                .help("add config path to dropwizard application configuration");

        subparser.addArgument("-s", "--send")
                .dest("send")
                .type(String.class)
                .required(true)
                .help("add file path to a Xml message template file to send to destination ");

        subparser.addArgument("-p", "--payload")
                .dest("payload")
                .type(String.class)
                .required(false)
                .help("add file path to a Xml payload template file to send to destination ");

        subparser.addArgument("-o", "--ouputDirectory")
                .dest("outputDirectory")
                .type(String.class)
                .required(false)
                .help("specify directory input path to create file with merged sent content");

        subparser.addArgument("-u", "--counterpartUrl")
                .dest("counterpartUrl")
                .type(String.class)
                .required(false)
                .help("specify counterpart Url to send message");
    }

    @Override
    public void run(Bootstrap bootstrap, Namespace namespace) throws Exception {

        Logger logger = LoggerFactory.getLogger("eu.cise.emulator.app.cli");
        String configpath = ((namespace.get("config") != null) ? namespace.get("config") : "./conf/cliconfig.yml");
        ConfigManager configManager = new ConfigManager(bootstrap);
        CiseEmulatorConfiguration emulatorConfig = configManager.readExistCiseEmulatorConfiguration(configpath);
        //bootstrap.setConfigurationSourceProvider(); urlconfigurationmanager-fileconfigurationmanager-resourceconfigurationmanager
        if (emulatorConfig==null) throw new CiseEmulatorConfigurationException("no configuration file found in expected location : "+configpath);
        XmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();
        Executor executor;
        String servicefile = namespace.getString("send");
        if ((servicefile.equals(""))) {
            System.out.println("ERROR:  add a template xml file to the command line expression with the \"-s\" option");
            return;
        }
        String payload = namespace.getString("payload");
        if (payload == null) payload = "";

        String outputDirectory = namespace.getString("outputDirectory");
        if (outputDirectory != null) emulatorConfig.setOutputDirectory(outputDirectory);
        String counterpartUrl= namespace.getString("counterpartUrl");
        if (counterpartUrl != null) emulatorConfig.setCounterpartUrl(counterpartUrl);

        executor = new Executor(new SourceStreamProcessor(),
                new Sender(),
                new SimLogger.Slf4j(),
                emulatorConfig,
                xmlMapper,
                validator);
        String pathDefault= namespace.getString("location");
        Message generatedMessage = executor.LoadMessage(servicefile, payload);

        String fileNameTemplate = emulatorConfig.getOutputDirectory() + emulatorConfig.getPublishedId() + "_out";
        String createdFile = InteractIOFile.createRef(fileNameTemplate, MESSAGE_MODAL,new StringBuffer(xmlMapper.toXML(generatedMessage)));

        boolean signSend = emulatorConfig.getSignatureOnSend().equals("true");
        RestResult obtainedResult = executor.sendEvent(generatedMessage, signSend);

        String AckCode = acknowledgementHelper.getAckCode(xmlMapper, obtainedResult.getBody());

        String createdfileResult = InteractIOFile.createRelativeRef(fileNameTemplate, createdFile,AckCode,MESSAGE_MODAL, new StringBuffer(obtainedResult.getBody()));
        if (!(obtainedResult.getCode().toString().equals(200)) && AckCode!=SUCCESS) LOGGER.warn(obtainedResult.getBody());
    }



    private CiseEmulatorConfiguration parseConfiguration(ConfigurationFactoryFactory configurationFactoryFactory, ConfigurationSourceProvider provider, Validator validator, String path, Class klass, ObjectMapper objectMapper) {
        CiseEmulatorConfiguration ciseEmulatorConfiguration = null;
        ConfigurationFactory<CiseEmulatorConfiguration> configurationFactory = configurationFactoryFactory.create(klass, validator, objectMapper, "dw");
        try {
            ciseEmulatorConfiguration = (path != null ? configurationFactory.build(provider, path) : configurationFactory.build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return ciseEmulatorConfiguration;
    }

}
