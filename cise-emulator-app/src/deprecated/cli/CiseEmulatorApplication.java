package eu.cise.emulator.deprecated.cli;

import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.eucise.xml.DefaultXmlMapper;
import eu.cise.emulator.deprecated.cli.cli.ClientCustomCommand;
import eu.cise.emulator.deprecated.cli.cli.ServerCustomCommand;
import eu.cise.emulator.deprecated.cli.core.Executor;
import eu.cise.emulator.deprecated.cli.core.InboundService;
import eu.cise.emulator.deprecated.cli.core.sub.MessageValidator;
import eu.cise.emulator.deprecated.cli.core.sub.Sender;
import eu.cise.emulator.deprecated.cli.core.sub.SourceStreamProcessor;
import eu.cise.emulator.deprecated.cli.resources.InboundRESTMessageService;
import eu.cise.emulator.deprecated.cli.transport.CISEMessageServiceImpl;
import eu.cise.emulator.deprecated.cli.util.SimLogger;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.xml.ws.Endpoint;

public class CiseEmulatorApplication extends Application<CiseEmulatorConfiguration> {

    // JAX-WS Bundle
    private static final JAXWSBundle<Object> JAXWS_BUNDLE;

    static {
        JAXWS_BUNDLE = new JAXWSBundle<>("/emu/soap");
    }


    public static void main(final String[] args) throws Exception {
        new CiseEmulatorApplication().run(args);
    }

    @Override
    public String getName() {
        return "cise-emulator";
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorConfiguration> bootstrap) {
       /*use ResourceConfigurationFileProvider as ConfigurationSourceProvider until path doubt solved
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());*/
        bootstrap.addBundle(JAXWS_BUNDLE);
        bootstrap.addCommand(new ClientCustomCommand());
        bootstrap.addCommand(new ServerCustomCommand());
    }

    @Override
    public void run(final CiseEmulatorConfiguration configuration,
                    final Environment environment) {

        // prepare common service(s) configuration (Cise Platform Messages interpreter and validator)
        DefaultXmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();

        Executor executor = new Executor(new SourceStreamProcessor(),
                new Sender(),
                new SimLogger.Slf4j(),
                configuration,
                xmlMapper,
                validator);
        // create the Inbound Service(s) (as server treatment of incoming Cise Platform Messages)
        String filenameTemplate = configuration.getInputDirectory() + "/" + configuration.getPublishedId();
        InboundService.init(executor, filenameTemplate);
        // create adequate transport to invoke the inbound service
        if (configuration.getServiceMode().toUpperCase().contains("SOAP")) { // WSDL first service using server side JAX-WS handler and CXF logging interceptors
            Endpoint e = JAXWS_BUNDLE.publishEndpoint(
                    new EndpointBuilder("/CISEMessageService", new CISEMessageServiceImpl()));
        }
        if (configuration.getServiceMode().toUpperCase().contains("REST")) {
            InboundRESTMessageService inboundRESTMessageService = new InboundRESTMessageService(configuration);
            environment.jersey().register(inboundRESTMessageService);
        }
    }
}
