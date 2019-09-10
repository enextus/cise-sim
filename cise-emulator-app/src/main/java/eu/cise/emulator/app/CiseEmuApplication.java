package eu.cise.emulator.app;

import eu.cise.emulator.app.cli.CommandLineSender;
import eu.cise.emulator.app.context.SimConfig;
import eu.cise.emulator.app.core.InstanceID;
import eu.cise.emulator.app.resource.InboundRESTMessageService;
import eu.cise.emulator.app.resource.OutBoundRestServiceAPI;
import eu.cise.emulator.app.transport.OutBoundWebSocketClient;
import eu.cise.emulator.app.transport.OutBoundWebSocketService;
import eu.cise.emulator.app.util.CrossOrigin;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.aeonbits.owner.ConfigFactory;

import java.util.concurrent.CountDownLatch;

public class CiseEmuApplication extends Application<CiseEmuConfiguration> {

    private static CiseEmuApplication appLiveRunning;
    private static Thread serverThread = null;
    private InstanceID instanceID = null;
    private SimConfig appConfig;
    private boolean InitWithParameter=false;
    private CountDownLatch serverStarted;

    public CiseEmuApplication(SimConfig refConfig, CountDownLatch aServerStarted, boolean withParameter) {
        super();
        this.serverStarted= aServerStarted;
        this.InitWithParameter = withParameter;
        this.appConfig =refConfig;
    }



    public static void main(final String[] args) throws Exception {
        CountDownLatch serverStarted = new CountDownLatch(1);
        if (args.length == 0) {
            final String[] argsDefault= new String[]{"server", "config.yml"};
            appLiveRunning = new CiseEmuApplication(createConfig(), serverStarted, false);
            appLiveRunning.run(argsDefault);
            //serverThread = new Thread(GeneralUtils.rethrow(() -> appLiveRunning.run(argsDefault))); // was fixed argument server config.yml (root of the directory jar -> class)
        } else {
            appLiveRunning = new CiseEmuApplication(createConfig(), serverStarted, true);
            appLiveRunning.run(args);
            //serverThread = new Thread(GeneralUtils.rethrow(() ->appLiveRunning.run(args))); // was fixed argument server config.yml (root of the directory jar -> class)
        }
        //serverThread.setDaemon(true);
        //serverThread.start();
    }


    private static SimConfig createConfig() {
        return ConfigFactory.create(SimConfig.class);
    }

    @Override
    public String getName() {
        return ("");
        //return (CiseEmuApplication.appLiveRunning!=null?CiseEmuApplication.getMemberId():"#ToBeLoaded#");
    }

    public static String getMemberId() {
       // final String render = CiseEmuApplication.appLiveRunning.instanceID.render(Optional.empty());
        final String render =  "test";
        return render;
    }

    public static String getMemberIdClient() {
       // final String render = CiseEmuApplication.appLiveRunning.instanceID.render(Optional.of(InstanceID.ACTOR_WEB_CLIENT));
        final String render =  "test";
        return render;
    }

    public static String getMemberIdServer() {
       // final String render = CiseEmuApplication.appLiveRunning.instanceID.render(Optional.of(InstanceID.ACTOR_WEB_SERVER));
        final String render =  "test";
        return render;
    }


    @Override
    public void initialize(final Bootstrap<CiseEmuConfiguration> bootstrap) {


        if (!this.InitWithParameter) {
            bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        }
        //by default would use the FileConfigurationSourceProvider that user must always provide
        // with server check or send

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

        this.instanceID = configuration.buildInstance();

        ////OUT : allow service to inform itself on the identity and option given by the configuration
        final OutBoundRestServiceAPI outRestMessageService = new OutBoundRestServiceAPI(this.instanceID,this.appConfig);
        environment.jersey().register(outRestMessageService);

        ////OUT : allow service to inform itself on the identity and option given by the configuration
        final OutBoundWebSocketClient outRestMessageclient = OutBoundWebSocketClient.build("8080"); //TODO set dynamic value to reflect via this.instanceID.getPort()

        // IN = to the infrastructure server members (node, adapter, emulator, simulator ...)
        final InboundRESTMessageService inboundRESTMessageService = new eu.cise.emulator.app.resource.InboundRESTMessageService(this.instanceID,this.appConfig, outRestMessageclient);
        environment.jersey().register(inboundRESTMessageService);



    }

}
