package eu.europa.ec.jrc.marex;

import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.europa.ec.jrc.marex.cli.ClientCustomCommand;
import eu.europa.ec.jrc.marex.cli.ServerCustomCommand;
import eu.europa.ec.jrc.marex.core.Executor;
import eu.europa.ec.jrc.marex.core.InboundService;
import eu.europa.ec.jrc.marex.core.sub.MessageValidator;
import eu.europa.ec.jrc.marex.core.sub.Sender;
import eu.europa.ec.jrc.marex.core.sub.SourceStreamProcessor;
import eu.europa.ec.jrc.marex.resources.InboundRESTMessageService;
import eu.europa.ec.jrc.marex.transport.CISEMessageServiceImpl;
import eu.europa.ec.jrc.marex.util.SimLogger;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import javax.xml.ws.Endpoint;

public class CiseEmulatorApplication extends Application<CiseEmulatorConfiguration> {

    // JAX-WS Bundle
    private static final JAXWSBundle<Object> jaxWsBundle ;
    private static final InboundService inboundService ;

    static {
        jaxWsBundle = new JAXWSBundle<>("/emu/soap");
        inboundService = new InboundService();
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
        bootstrap.addBundle(jaxWsBundle);
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
        String filenameTemplate = configuration.getInputDirectory() +"/"+ configuration.getPublishedId();
        InboundService.init(executor,filenameTemplate);
        // create adequate transport to invoke the inbound service
        if (configuration.getServiceMode().toUpperCase().contains("SOAP")  ) { // WSDL first service using server side JAX-WS handler and CXF logging interceptors
            Endpoint e = jaxWsBundle.publishEndpoint(
                    new EndpointBuilder("/CISEMessageService", new CISEMessageServiceImpl())
                            .cxfInInterceptors(new LoggingInInterceptor())
                            .cxfOutInterceptors(new LoggingOutInterceptor()));
        }
        if (configuration.getServiceMode().toUpperCase().contains("REST")  ) {
            InboundRESTMessageService inboundRESTMessageService = new InboundRESTMessageService(configuration);
            environment.jersey().register(inboundRESTMessageService);
        }
    }
}
