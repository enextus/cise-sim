package eu.cise.emulator.api.helpers;

import com.codahale.metrics.MetricRegistry;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.EmulatorApp;
import eu.cise.emulator.api.EmulatorDropwizardConf;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.TemplateLoader;
import io.dropwizard.Application;
import io.dropwizard.cli.ServerCommand;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.lang.reflect.Field;
import java.util.List;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;


/**
 * A utility to run DropWizard (http://dropwizard.io/) applications in-process.
 */
public class DropWizardServerBuilder {

    public static <T extends EmulatorDropwizardConf> DropWizardServer<T> createServer(
        String configFile,
        Class<? extends Application<T>> applicationClass,
        MessageProcessor messageProcessor,
        MessageStorage messageStorage,
        EmuConfig emuConfig,
        TemplateLoader templateLoader) throws Exception {
        // Create application
        final Application<T> application = applicationClass.getConstructor().newInstance();

        // Create bootstrap
        final ServerCommand<T> serverCommand = new ServerCommand<T>(application);
        final Bootstrap<T> bootstrap = new Bootstrap<T>(application);
        bootstrap.addCommand(serverCommand);

        // Write a temporary config file
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        ConfigurationFactory<T> configurationFactory
            = bootstrap.getConfigurationFactoryFactory()
            .create((Class<T>) EmulatorDropwizardConf.class,
                bootstrap.getValidatorFactory().getValidator(),
                bootstrap.getObjectMapper(),
                "dw");
        final T builtConfig = configurationFactory.build(
            bootstrap.getConfigurationSourceProvider(), configFile);

        // Configure logging
        builtConfig.getLoggingFactory()
            .configure(bootstrap.getMetricRegistry(),
                bootstrap.getApplication().getName());

        // Environment
        final Environment environment = new Environment(bootstrap.getApplication().getName(),
            bootstrap.getObjectMapper(),
            bootstrap.getValidatorFactory().getValidator(),
            bootstrap.getMetricRegistry(),
            bootstrap.getClassLoader());

        // Initialize environment
        builtConfig.getMetricsFactory()
            .configure(environment.lifecycle(), bootstrap.getMetricRegistry());
        builtConfig.setEmuConfig(emuConfig);
        // Server
        final Server server = builtConfig.getServerFactory().build(environment);
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStopped(LifeCycle event) {
                builtConfig.getLoggingFactory().stop();
            }
        });

        DropWizardServer serverReady = new DropWizardServer(
            builtConfig,
            bootstrap,
            application,
            environment,
            server,
            environment.metrics(),
            messageProcessor,
            messageStorage,
            templateLoader);
        serverReady.start();
        return serverReady;
    }


    public static class DropWizardServer<T extends EmulatorDropwizardConf> extends EmulatorApp {

        private final T builtConfig;
        private final Bootstrap<T> bootstrap;
        private final Application<T> application;
        private final Environment environment;
        private final Server jettyServer;
        private final MetricRegistry metricRegistry;


        DropWizardServer(T builtConfig,
            Bootstrap<T> bootstrap,
            Application<T> application,
            Environment environment,
            Server jettyServer,
            MetricRegistry metricRegistry,
            MessageProcessor messageProcessor,
            MessageStorage messageStorage,
            TemplateLoader templateLoader) {
            this.builtConfig = builtConfig;
            this.bootstrap = bootstrap;
            this.application = application;
            this.environment = environment;
            this.jettyServer = jettyServer;
            this.metricRegistry = metricRegistry;
            this.builtConfig.setMessageProcessor(messageProcessor);
            this.builtConfig.setMessageStorage(messageStorage);
            this.builtConfig.setTemplateLoader(templateLoader);
        }

        /**
         * Returns the server's metric registry.
         */
        public MetricRegistry getMetricRegistry() {
            return metricRegistry;
        }

        /**
         * Runs lifecycle start and starts server.
         */
        public void start() throws Exception {
            application.initialize(bootstrap);
            bootstrap.run(builtConfig, environment);
            application.run(builtConfig, environment);
            toggleManagedObjects(true);
            jettyServer.start();
        }

        /**
         * Runs lifecycle stop and stops server.
         */
        public void stop() throws Exception {
            jettyServer.stop();
            toggleManagedObjects(false);
        }

        @SuppressWarnings("unchecked")
        private void toggleManagedObjects(boolean start) throws Exception {
            Field managedObjectsField = environment.lifecycle().getClass()
                .getDeclaredField("managedObjects");
            managedObjectsField.setAccessible(true);
            List<LifeCycle> managedObjects = (List<LifeCycle>) managedObjectsField
                .get(environment.lifecycle());
            for (LifeCycle managedObject : managedObjects) {
                if (start) {
                    managedObject.start();
                } else {
                    managedObject.stop();
                }
            }
        }
    }
}
