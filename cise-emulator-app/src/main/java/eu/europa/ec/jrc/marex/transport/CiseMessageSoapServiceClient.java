package eu.europa.ec.jrc.marex.transport;

import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;
import eu.europa.ec.jrc.marex.client.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Configuration;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;


public class CiseMessageSoapServiceClient {
    private String wsdlURL;
    private static final String SERVICE_URI = "http://www.cise.eu/accesspoint/service/v1/";
    private static final String LOCAL_URI = "CISEMessageService";
    private static final String SERVICE_PORT = "CISEMessageServiceSoapPort";//"CISEMessageServiceSoapImpl";
    private XmlMapper xmlmapper;
    Logger logger = LoggerFactory.getLogger(CiseMessageSoapServiceClient.class);


    public CiseMessageSoapServiceClient(String urlString, XmlMapper mapper) throws MalformedURLException {
        this.wsdlURL = urlString;
        this.xmlmapper = mapper;
    }


    public SendResult send(Message message) {
        Acknowledgement resultAcknowledgement = new Acknowledgement();
        try {

            URL wsdlURL = new URL(this.wsdlURL);
            //fail safe ... can access the url
            QName SERVICE_QNAME = new QName(CiseMessageSoapServiceClient.SERVICE_URI, CiseMessageSoapServiceClient.LOCAL_URI);
            Service service = Service.create(wsdlURL, SERVICE_QNAME);
            QName PORT_QNAME = new QName(CiseMessageSoapServiceClient.SERVICE_URI, CiseMessageSoapServiceClient.SERVICE_PORT);//, CiseMessageSoapServiceClient.SERVICE_PORT
            CISEMessageServiceSoapImpl client = service.getPort(PORT_QNAME, CISEMessageServiceSoapImpl.class);
            // resultAcknowledgement = client.send(message);


            //Service service = Service.create(wsdlURL, new QName(CiseMessageSoapServiceClient.LOCAL_URI));
            Dispatch<Source> disp = service.createDispatch(new QName(CiseMessageSoapServiceClient.SERVICE_PORT), Source.class, Service.Mode.PAYLOAD);

            Source request = new StreamSource(xmlmapper.toXML(message));
            Source response = disp.invoke(request);

            //resultAcknowledgement = client.send(message);
        } catch (Exception e) {
            logger.error("fail to send to " + this.wsdlURL, e);
            resultAcknowledgement.setMessageID(message.getMessageID());
            resultAcknowledgement.setPriority(message.getPriority());
            resultAcknowledgement.setCorrelationID(message.getMessageID());
            resultAcknowledgement.setCorrelationID(message.getMessageID());
            resultAcknowledgement.setCreationDateTime(message.getCreationDateTime());
            resultAcknowledgement.setAckCode(AcknowledgementType.ENTITY_TYPE_NOT_ACCEPTED);
            resultAcknowledgement.setAckDetail(e.getMessage());
            resultAcknowledgement.setRequiresAck(message.isRequiresAck());
            resultAcknowledgement.setSender(message.getRecipient());
        }
        return new SendResult(200, xmlmapper.toXML(resultAcknowledgement), "");
    }


    public Configuration getConfiguration() {
        return null;
    }

}


/* other option use a dispatch api https://cxf.apache.org/docs/how-do-i-develop-a-client.html
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
...

URL wsdlURL = new URL("http://localhost/hello?wsdl");
Service service = Service.create(wsdlURL, new QName("HelloService"));
Dispatch<Source> disp = service.createDispatch(new QName("HelloPort"), Source.class, Service.Mode.PAYLOAD);

Source request = new StreamSource("<hello/>")
Source response = disp.invoke(request);


            QName SERVICE_QNAME = new QName(CiseMessageSoapServiceClient.SERVICE_URI, CiseMessageSoapServiceClient.LOCAL_URI);
            Service service = Service.create(new URL(this.wsdlURL), SERVICE_QNAME);
            CISEMessageServiceSoapImpl client = service.getPort(CISEMessageServiceSoapImpl.class);
            resultAcknowledgement = client.send(message);

 */