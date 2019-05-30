package eu.cise.sim.app.context;

import eu.eucise.servicemodel.v1.message.Message;
import eu.cise.sim.transport.conformance.Consumers;
import eu.cise.sim.transport.conformance.GatewayProcessor;
import eu.eucise.xml.XmlMapper;



class ConsumersAppContext {
    private GatewayProcessor instanceGP;
    private Consumers consumers;

    public ConsumersAppContext() {

    }

    public GatewayProcessor makeGatewayProcessor() {
        if (instanceGP==null) instanceGP=new GatewayProcessor() {
            @Override
            public void process(Message message) {
                System.out.println(".");
            }
        };
                return instanceGP;
    }

    public Consumers getConsumers() {
        return consumers;
    }

    public XmlMapper getDefaultXmlMapper() {
        return getDefaultXmlMapper();
    }

}
