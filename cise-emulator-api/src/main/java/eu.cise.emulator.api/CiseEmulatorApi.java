package eu.cise.emulator.api;

import eu.cise.emulator.Configuration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CiseEmulatorApi extends Application<CiseEmulatorConf> {
    private Configuration appConfiguration;

    public static void main(final String[] args) throws Exception {
        new CiseEmulatorApi().run(args);
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorConf> bootstrap) {
//        CiseEmulatorApp.init(bootstrap);

    }

    @Override
    public void run(final CiseEmulatorConf configuration,
                    final Environment environment) {

       /* this.instanceID = configuration.buildInstance();*/
       // this.appConfiguration = new Configuration (configuration);


////OUT : allow service to inform itself on the identity and option given by the configuration
       /* final  outRestMessageService = new OutBoundRestServiceAPI(this.instanceID, configuration);
        environment.jersey().register(outRestMessageService);
*/
        ////OUT : allow service to inform itself on the identity and option given by the configuration
    /*    final OutBoundWebSocketClient outRestMessageClient = OutBoundWebSocketClient.build("8080"); //TODO set dynamic value to reflect via this.instanceID.getPort()
  */  }

}


