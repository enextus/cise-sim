package eu.cise.emulator;

import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
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

    @Key("sender.service-type")
    ServiceType serviceType();

    @Key("sender.service-operation")
    ServiceOperationType serviceOperation();

    @Key("destination.endpoint-url")
    String endpointUrl();
}
