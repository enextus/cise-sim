package eu.cise.emulator.deprecated.web.integration.Validation;

import eu.eucise.xml.XmlMapper;

public interface Consumers {

    void launch(GatewayProcessor gatewayProcessor, XmlMapper xmlMapper);

    void shutDown();

}
