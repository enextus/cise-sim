package eu.cise.emulator.api;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CiseEmulatorApi extends Application<CiseEmulatorConf> {

    public static void main(final String[] args) throws Exception {
        new CiseEmulatorApi().run(args);
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorConf> bootstrap) {

    }

    @Override
    public void run(final CiseEmulatorConf configuration, final Environment environment) {

    }

}


