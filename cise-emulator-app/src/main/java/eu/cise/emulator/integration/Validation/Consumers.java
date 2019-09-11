package eu.cise.emulator.integration.Validation;

import eu.eucise.xml.XmlMapper;

public interface Consumers {

    void launch(GatewayProcessor gatewayProcessor, XmlMapper xmlMapper);

    void shutDown();

}
