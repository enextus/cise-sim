package eu.europa.ec.jrc.marex.transport;

import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.europa.ec.jrc.marex.core.InboundService;

import javax.jws.HandlerChain;
import javax.jws.WebService;

@WebService(serviceName = "CISEMessageService",
        portName = "CISEMessageServiceSoapPort",
        endpointInterface = "eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl",
        targetNamespace = "http://www.cise.eu/accesspoint/service/v1/",
        wsdlLocation = "META-INF/CISEMessageService.wsdl")
@HandlerChain(file = "wsdlCISEMessageService-handlerchain.xml")
public class CISEMessageServiceImpl implements CISEMessageServiceSoapImpl {
    private final InboundService inboundService = new InboundService();

    @Override
    public Acknowledgement send(Message message) {
        return inboundService.receive(message);
    }
}
