package eu.cise.emulator.deprecated.cli.transport;

import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;
import eu.cise.emulator.deprecated.cli.client.SendResult;
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

/* other option use a dispatch api https://cxf.apache.org/docs/how-do-i-develop-a-client.html
 */

public class CiseMessageSoapServiceClient {
    private String wsdlURL;
    private static final String SERVICE_URI = "http://www.cise.eu/accesspoint/service/v1/";
    private static final String LOCAL_URI = "CISEMessageService";
    private static final String SERVICE_PORT = "CISEMessageServiceSoapPort"; //"CISEMessageServiceSoapImpl";
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
            QName serviceQname = new QName(CiseMessageSoapServiceClient.SERVICE_URI, CiseMessageSoapServiceClient.LOCAL_URI);
            Service service = Service.create(wsdlURL, serviceQname);
            QName portQname = new QName(CiseMessageSoapServiceClient.SERVICE_URI, CiseMessageSoapServiceClient.SERVICE_PORT);
            CISEMessageServiceSoapImpl client = service.getPort(portQname, CISEMessageServiceSoapImpl.class);
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

