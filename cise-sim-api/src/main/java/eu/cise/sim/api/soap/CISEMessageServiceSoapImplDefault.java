package eu.cise.sim.api.soap;

import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.ResponseApi;
import eu.eucise.xml.XmlMapper;

import javax.jws.WebService;

@WebService(serviceName = "CISEMessageService", portName = "CISEMessageServiceSoapPort",
        endpointInterface = "eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/", wsdlLocation = "META-INF/CISEMessageService.wsdl")

public class CISEMessageServiceSoapImplDefault implements CISEMessageServiceSoapImpl {
    private static MessageAPI messageAPI;
    private static XmlMapper xmlMapper;

    public CISEMessageServiceSoapImplDefault(MessageAPI messageAPI, XmlMapper xmlMapper) {
        CISEMessageServiceSoapImplDefault.messageAPI = messageAPI;
        CISEMessageServiceSoapImplDefault.xmlMapper = xmlMapper;
    }

    public CISEMessageServiceSoapImplDefault() {
    }

    /* TODO :  improve the implementation by following the bundle life cycle instead of post creation static variable setting */
    public static void setMessageAPI(MessageAPI messageAPI) {
        CISEMessageServiceSoapImplDefault.messageAPI = messageAPI;
    }

    public static void setXmlMapper(XmlMapper xmlMapper) {
        CISEMessageServiceSoapImplDefault.xmlMapper = xmlMapper;
    }

    @Override
    public Acknowledgement send(Message message) {
        ResponseApi<Acknowledgement> response = messageAPI.receive(message);
        return response.getResult();
    }

}
