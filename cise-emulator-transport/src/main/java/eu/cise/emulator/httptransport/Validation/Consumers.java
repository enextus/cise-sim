package eu.cise.emulator.httptransport.Validation;

import eu.eucise.xml.XmlMapper;

public interface Consumers {

    void launch(GatewayProcessor gatewayProcessor, XmlMapper xmlMapper);

    void shutDown();

}
