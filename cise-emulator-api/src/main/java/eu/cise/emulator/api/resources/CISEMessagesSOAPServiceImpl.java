package eu.cise.emulator.api.resources;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;

import javax.jws.HandlerChain;
import javax.jws.WebService;


/*@WebService(endpointInterface = "eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl",
        targetNamespace = "http://www.cise.eu/servicemodel/v1/service",
        name = "CISEMessageService",
        wsdlLocation = "META-INF/CISEMessageService.wsdl")*/
@WebService(serviceName = "CISEMessageService", portName = "CISEMessageServiceSoapPort",
        endpointInterface = "eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/", wsdlLocation = "META-INF/CISEMessageService.wsdl")
//@HandlerChain(file = "WsChainhandler.xml")
public class CISEMessagesSOAPServiceImpl implements CISEMessageServiceSoapImpl {


    @Override
    public Acknowledgement send(Message message) {
        System.out.println("touch");
        return null;
    }

}
