
package eu.cise.dispatcher.soap;

import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.event.Event;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.ObjectFactory;
import eu.eucise.xml.Services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebService(name = "CISEMessageServiceSoapImpl", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/")
@XmlSeeAlso({
        ObjectFactory.class, Message.class, Entity.class, Services.class, Event.class
})
public interface CISEMessageServiceSoapImpl {


    /**
     * @param message
     * @return returns eu.cise.dispatcher.soap.Acknowledgement
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "send", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/", className = "eu.cise.dispatcher.soap.Send")
    @ResponseWrapper(localName = "sendResponse", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/", className = "eu.cise.dispatcher.soap.SendResponse")
    Acknowledgement send(
            @WebParam(name = "message", targetNamespace = "")
                    Message message);
}
