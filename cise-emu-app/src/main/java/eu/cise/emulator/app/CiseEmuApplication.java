package eu.cise.emulator.app;

import com.google.common.io.Resources;
import eu.cise.emulator.app.cli.RenderCommand;
import eu.cise.emulator.app.core.InstanceID;
import eu.cise.emulator.app.resources.MessageRestProcess;
import eu.cise.emulator.app.resources.MessageSoapProcess;
import eu.cise.emulator.httptransport.ServerRestProvider;
import eu.cise.emulator.httptransport.ServerSoapProvider;
import eu.cise.emulator.websocket.server.IhmWebSocket;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.websockets.GeneralUtils;
import io.dropwizard.websockets.WebsocketBundle;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CiseEmuApplication extends Application<CiseEmuConfiguration> {


    private io.dropwizard.websockets.WebsocketBundle websocketBundle;

    private final CountDownLatch cdl;

    CiseEmuApplication(CountDownLatch cdl) {
        this.cdl = cdl;
    }
    public static void main(final String[] args) throws Exception {
        CountDownLatch serverStarted = new CountDownLatch(1);
        Thread serverThread = new Thread(GeneralUtils.rethrow(() -> new CiseEmuApplication(serverStarted).run(new String[]{"server", Resources.getResource("config.yml").getPath()})));
        serverThread.setDaemon(true);
        serverThread.start();
        serverStarted.await(10, SECONDS);
    }

    @Override
    public String getName() {
        return "Cise-Emulator";
    }


    @Override
    public void initialize(final Bootstrap<CiseEmuConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
     /*   bootstrap.setConfigurationSourceProvider(             new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );*/

        WebsocketBundle websocketBundle = new WebsocketBundle(IhmWebSocket.class);
        bootstrap.addBundle(websocketBundle);

        bootstrap.addCommand(new RenderCommand());
        //bootstrap.addBundle(jaxWsBundle);
        //bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(final CiseEmuConfiguration configuration,
                    final Environment environment) {
        final InstanceID instance = configuration.buildInstance();
        //environment.healthChecks().register("Instance", new InstanceHealthCheck(instance));
        //environment.admin().addTask(new EchoTask());
        if (true) {
            environment.jersey().register(ServerRestProvider.getInstance());
            environment.jersey().register(new MessageRestProcess(instance));
        }else {
            environment.jersey().register(ServerSoapProvider.getInstance());
            environment.jersey().register(new MessageSoapProcess(instance));
        }
         // final ServerEndpointConfig config = ServerEndpointConfig.Builder.create(postWebSocket.class, "/extends-ws").build();
         // websocketBundle.addEndpoint(config);

    }



}
