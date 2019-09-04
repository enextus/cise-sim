package eu.europa.ec.jrc.marex;

import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.europa.ec.jrc.marex.cli.ClientCustomCommand;
import eu.europa.ec.jrc.marex.cli.ServerCustomCommand;
import eu.europa.ec.jrc.marex.core.InboundService;
import eu.europa.ec.jrc.marex.resources.InboundRESTMessageService;
import eu.europa.ec.jrc.marex.transport.CiseMessageServiceSoapDelegate;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import javax.xml.ws.Endpoint;

public class CiseEmulatorApplication extends Application<CiseEmulatorConfiguration> {

    // JAX-WS Bundle
    private JAXWSBundle<Object> jaxWsBundle = new JAXWSBundle<>("/emu/soap");



    public static void main(final String[] args) throws Exception {
        new CiseEmulatorApplication().run(args);
    }

    @Override
    public String getName() {
        return "cise-emulator";
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorConfiguration> bootstrap) {
        if (!true) {
            bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        }
        bootstrap.addBundle(jaxWsBundle);

        bootstrap.addCommand(new ClientCustomCommand());
        bootstrap.addCommand(new ServerCustomCommand());


    }

    @Override
    public void run(final CiseEmulatorConfiguration configuration,
                    final Environment environment) {
        // WSDL first service using server side JAX-WS handler and CXF logging interceptors
        Endpoint e = jaxWsBundle.publishEndpoint(
                new EndpointBuilder("/CISEMessageService", new CiseMessageServiceSoapDelegate())
                        .cxfInInterceptors(new LoggingInInterceptor())
                        .cxfOutInterceptors(new LoggingOutInterceptor()));

        final InboundRESTMessageService inboundRESTMessageService = new InboundRESTMessageService(configuration);
        environment.jersey().register(inboundRESTMessageService);
    }



}
