package eu.cise.emulator.app.context;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.emulator.httptransport.conformance.Consumers;
import eu.cise.emulator.httptransport.conformance.GatewayProcessor;
import eu.eucise.xml.XmlMapper;



class Context {
    private GatewayProcessor instanceGP;
    private Consumers consumers;

    public Context() {

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
