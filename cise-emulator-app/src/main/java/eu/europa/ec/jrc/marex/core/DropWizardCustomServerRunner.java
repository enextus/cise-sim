package eu.europa.ec.jrc.marex.core;

import com.codahale.metrics.MetricRegistry;
import eu.europa.ec.jrc.marex.CiseEmulatorApplication;
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration;
import io.dropwizard.Application;
import io.dropwizard.cli.ServerCommand;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;


/**
 * A utility to run DropWizard (http://dropwizard.io/) applications in-process.
 */
public class DropWizardCustomServerRunner {

    public static File tmpConfigFile;

    public static <T extends CiseEmulatorConfiguration> DropWizardServer<T> createServer(
            T config,
            Class<? extends Application<T>> applicationClass) throws Exception {
        // Create application
        final Application<T> application = applicationClass.getConstructor().newInstance();

        // Create bootstrap
        final ServerCommand<T> serverCommand = new ServerCommand<T>(application);
        final Bootstrap<T> bootstrap = new Bootstrap<T>(application);
        bootstrap.addCommand(serverCommand);

        // Write a temporary config file
        tmpConfigFile = new File(
                System.getProperty("java.io.tmpdir"),
                config.getClass().getCanonicalName() + "_" + System.currentTimeMillis());
        //tmpConfigFile.deleteOnExit();
        bootstrap.getObjectMapper().writeValue(tmpConfigFile, config);
        System.out.println(" config was writed to " + tmpConfigFile.toPath().toAbsolutePath().toString());
        // Parse configuration
        ConfigurationFactory<T> configurationFactory
                = bootstrap.getConfigurationFactoryFactory()
                .create((Class<T>) config.getClass(),
                        bootstrap.getValidatorFactory().getValidator(),
                        bootstrap.getObjectMapper(),
                        "dw");
        final T builtConfig = configurationFactory.build(
                bootstrap.getConfigurationSourceProvider(), tmpConfigFile.getAbsolutePath());

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
        builtConfig.getMetricsFactory().configure(environment.lifecycle(), bootstrap.getMetricRegistry());

        // Server
        final Server server = builtConfig.getServerFactory().build(environment);
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStopped(LifeCycle event) {
                builtConfig.getLoggingFactory().stop();
            }
        });

        return new DropWizardServer((CiseEmulatorConfiguration) builtConfig, bootstrap, application, environment, server, environment.metrics());
    }

    public static class DropWizardServer<T extends CiseEmulatorConfiguration> extends CiseEmulatorApplication {
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
                         MetricRegistry metricRegistry) {
            this.builtConfig = builtConfig;
            this.bootstrap = bootstrap;
            this.application = application;
            this.environment = environment;
            this.jettyServer = jettyServer;
            this.metricRegistry = metricRegistry;
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
            Field managedObjectsField = environment.lifecycle().getClass().getDeclaredField("managedObjects");
            managedObjectsField.setAccessible(true);
            List<LifeCycle> managedObjects = (List<LifeCycle>) managedObjectsField.get(environment.lifecycle());
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
