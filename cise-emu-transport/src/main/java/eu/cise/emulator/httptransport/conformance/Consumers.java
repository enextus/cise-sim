package eu.cise.emulator.httptransport.conformance;

        import eu.eucise.xml.XmlMapper;

public interface Consumers {

    void launch(GatewayProcessor gatewayProcessor , XmlMapper xmlMapper);
    void shutDown();

}
