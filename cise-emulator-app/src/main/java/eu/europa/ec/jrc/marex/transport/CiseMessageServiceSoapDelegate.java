package eu.europa.ec.jrc.marex.transport;

import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.accesspoint.service.v1.ObjectFactory;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.europa.ec.jrc.marex.core.InboundService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = "http://marex.jrc.ec.europa.eu/Service", name = "CiseMessageServiceSoapDelegate")
@XmlSeeAlso({eu.cise.servicemodel.v1.service.ObjectFactory.class, eu.cise.servicemodel.v1.authority.ObjectFactory.class, ObjectFactory.class, eu.cise.servicemodel.v1.message.ObjectFactory.class})
public class CiseMessageServiceSoapDelegate implements CISEMessageServiceSoapImpl {
    private final InboundService inboundService = new InboundService();
    public void CiseMessageServiceSoapDelegate() {

    }

    @Override
    @WebMethod
    @RequestWrapper(localName = "send", targetNamespace = "http://marex.jrc.ec.europa.eu/Service", className = "eu.cise.accesspoint.service.v1.Send")
    @ResponseWrapper(localName = "sendResponse", targetNamespace = "http://marex.jrc.ec.europa.eu/Service", className = "eu.cise.accesspoint.service.v1.SendResponse")
    @WebResult(name = "return", targetNamespace = "http://marex.jrc.ec.europa.eu/Service")
    public eu.cise.servicemodel.v1.message.Acknowledgement send( @WebParam(name = "message", targetNamespace = "") eu.cise.servicemodel.v1.message.Message message  ){
            return inboundService.Receive(message);
    }

}
