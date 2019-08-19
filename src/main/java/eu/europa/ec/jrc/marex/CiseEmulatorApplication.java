package eu.europa.ec.jrc.marex;

import eu.europa.ec.jrc.marex.cli.ClientCustomCommand;
import eu.europa.ec.jrc.marex.cli.ServerCustomCommand;
import eu.europa.ec.jrc.marex.resources.InboundRESTMessageService;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CiseEmulatorApplication extends Application<CiseEmulatorConfiguration> {





    public static void main(final String[] args) throws Exception {
        new CiseEmulatorApplication().run(args);
    }

    @Override
    public String getName() {
        return "cise-emulator";
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorConfiguration> bootstrap) {
        if (!true) {bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider()); }
        bootstrap.addCommand(new ClientCustomCommand());
        bootstrap.addCommand(new ServerCustomCommand());



    }

    @Override
    public void run(final CiseEmulatorConfiguration configuration,
                    final Environment environment) {
        final InboundRESTMessageService inboundRESTMessageService = new InboundRESTMessageService(configuration);
        environment.jersey().register(inboundRESTMessageService);
    }

}
