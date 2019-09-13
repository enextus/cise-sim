package eu.cise.emulator;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

/**
 * This file is containing the emulator application configuration
 */
@Sources({"file:${conf.dir}ais-adaptor.properties",
        "classpath:ais-adaptor.properties"})
public interface EmuConfig extends Config {

    @Key("sender.service-id")
    String serviceId();

}
