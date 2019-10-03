package eu.cise.emulator.api;

import eu.cise.emulator.api.helpers.CrossOriginSupport;
import eu.cise.emulator.api.helpers.ServerExceptionMapper;
import eu.cise.emulator.api.resources.AssetRedirectionResource;
import eu.cise.emulator.api.resources.CiseMessageResource;
import eu.cise.emulator.api.resources.TemplateResource;
import eu.cise.emulator.api.resources.WebAPIMessageResource;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CiseEmulatorAPI extends Application<CiseEmulatorDropwizardConf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAPIMessageResource.class);
    private static boolean requiredFileConfig = false;

    public static void main(final String[] args) throws Exception {
        if (args.length > 0 && args[1].contains("/")) requiredFileConfig = true;
        new CiseEmulatorAPI().run(args);
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorDropwizardConf> bootstrap) {
        //use boot criteria to allow file instead of resource when path is provided
        if (!requiredFileConfig) {
            bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        }
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        bootstrap.addBundle(new ConfiguredAssetsBundle("/assets/", "/base/", "index.html")); // imply redirect from root ?
    }

    @Override
    public void run(final CiseEmulatorDropwizardConf configuration, final Environment environment) {
        environment.jersey().register(new ServerExceptionMapper());
        environment.jersey().setUrlPattern("/*"); // api/(rest/soap) and apiweb can then defined by specific resources
        LOGGER.info("Registering REST resources with crossOriginSupport");
        CrossOriginSupport.setup(environment);

        // delegate the principal application configurations interfaces (IOC)
        MessageAPI messageAPI = new DefaultMessageAPI(configuration.getMessageProcessor(), configuration.getMessageStorage(), configuration.getEmuConfig());
        LOGGER.info("Registering REST resources ");
        environment.jersey().register(new WebAPIMessageResource(messageAPI));
        environment.jersey().register(new CiseMessageResource(messageAPI, configuration.getMessageStorage()));
        environment.jersey().register(new AssetRedirectionResource());

        environment.jersey().register(new TemplateResource(messageAPI, new TemplateAPI(configuration.getMessageProcessor(), configuration.getTemplateLoader()), configuration.getEmuConfig()));

    }
}



