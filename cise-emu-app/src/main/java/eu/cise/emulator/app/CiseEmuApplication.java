package eu.cise.emulator.app;

import eu.cise.emulator.app.cli.CommandLineSender;
import eu.cise.emulator.app.core.InstanceID;
import eu.cise.emulator.app.resource.InboundRESTMessageService;
import eu.cise.emulator.app.resource.OutBoundRestServiceAPI;
import eu.cise.emulator.app.util.CrossOrigin;
import eu.cise.emulator.websocket.server.OutBoundWebSocketCheck;
import eu.cise.emulator.websocket.server.OutBoundWebSocketService;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.websockets.GeneralUtils;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CiseEmuApplication extends Application<CiseEmuConfiguration> {

    private static CiseEmuApplication appLiveRunning;
    private static Thread serverThread = null;
    private InstanceID instanceID = null;
    private String Version = "0.0.1";

    private final CountDownLatch cdl;
    private final boolean InitWithParameter;

    public CiseEmuApplication(CountDownLatch cdl, boolean InitWithParameter) {
        this.cdl = cdl;
        this.InitWithParameter = InitWithParameter;
    }

    public CiseEmuApplication(CountDownLatch cdl) {
        this.cdl = cdl;
        this.InitWithParameter = false;
    }


    public static void main(final String[] args) throws Exception {
        CountDownLatch serverStarted = new CountDownLatch(1);

        if (args.length == 0) {
            final String[] argsDefault = new String[]{"server", "config.yml"};
            appLiveRunning = new CiseEmuApplication(serverStarted);
            serverThread = new Thread(GeneralUtils.rethrow(() -> appLiveRunning.run(argsDefault))); // was fixed argument server config.yml (root of the directory jar -> class)
        } else {
            appLiveRunning = new CiseEmuApplication(serverStarted, true);
            serverThread = new Thread(GeneralUtils.rethrow(() -> appLiveRunning.run(args))); // was fixed argument server config.yml (root of the directory jar -> class)
        }

        serverThread.setDaemon(true);
        serverThread.start();
        serverStarted.await(10, SECONDS);
    }


    @Override
    public String getName() {
        return "emulator";
    }//+":"+CiseEmuApplication.appLiveRunning.instanceID.getName();}


    public static String getMemberId() {
        return (CiseEmuApplication.appLiveRunning.instanceID.getName());
    }

    public static String getMemberVersion() {
        return (CiseEmuApplication.appLiveRunning.getVersion());
    }


    @Override
    public void initialize(final Bootstrap<CiseEmuConfiguration> bootstrap) {


        if (!InitWithParameter) {
            bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        }
        //by default would use the FileConfigurationSourceProvider that user must always provide with server check or send

        bootstrap.addBundle(new io.dropwizard.websockets.WebsocketBundle(OutBoundWebSocketService.class));

        bootstrap.addCommand(new CommandLineSender());

        //// todo: allow service to serve the InBound request also in SOAP mode
        // bootstrap.addBundle(jaxWsBundle);

        //// todo: allow service to serve as asset : the client html+javascript
        //bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(final CiseEmuConfiguration configuration, final Environment environment) {
        CrossOrigin.setup(environment);

        String version = "0.0.1"; //temporally hard coded
        appLiveRunning.instanceID = configuration.buildInstance();
        ////OUT : allow service to inform itself on the identity and option given by the configuration
        final OutBoundRestServiceAPI outRestMessageService = new OutBoundRestServiceAPI(getVersion(), instanceID);
        environment.jersey().register(outRestMessageService);

        // OUT = check the web socket is up
        final OutBoundWebSocketCheck outWebSocketCheckService = new OutBoundWebSocketCheck(version);
        environment.healthChecks().register("OUT_WebSocket", outWebSocketCheckService);

        // IN = to the infrastructure server members (node, adapter, emulator, simulator ...)
        final InboundRESTMessageService inboundRESTMessageService = new eu.cise.emulator.app.resource.InboundRESTMessageService(version, instanceID);
        environment.jersey().register(inboundRESTMessageService);


    }


    public String getVersion() {
        return Version;
    }
}
