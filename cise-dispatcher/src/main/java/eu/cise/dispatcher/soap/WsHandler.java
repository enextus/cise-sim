package eu.cise.dispatcher.soap;

import com.sun.xml.messaging.saaj.soap.impl.TextImpl;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

@WebService(name = "WsHandler", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/")
@HandlerChain(file = "handlers.xml")
public class WsHandler implements SOAPHandler<SOAPMessageContext> {
    private static final String NAMESPACE_URI = "default";
    private static Document thisDocument;
    private final String VALID_PROPERTY = "RANDOM";
    @Resource
    WebServiceContext ctx;

    @WebMethod()


    public String getProperty(String propertyName) {
        return (String) ctx.getMessageContext().get(propertyName);
    }


    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    public boolean handleMessage(SOAPMessageContext messageContext) {
        Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        final SOAPMessage soapMessage = messageContext.getMessage();


        if (outboundProperty.booleanValue()) {
            try {
                SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
                thisDocument = soapMessage.getSOAPPart().getOwnerDocument();
                // Grab the header of the SOAP envelop
                SOAPHeader soapHeader = soapEnvelope.getHeader();
                // Attach a new header if there is none...
                if (soapHeader == null) {
                    soapHeader = soapEnvelope.addHeader();
                }
                SOAPPart soapPart = messageContext.getMessage().getSOAPPart();
                SOAPEnvelope env = soapPart.getEnvelope();
                SOAPBody soapBody = env.getBody();//get body from envelope

                QName inicialQname = null;
                if (soapBody != null) {
                    Iterator nodes = soapBody.getChildElements();
                    SOAPElement soapElement = null;
                    while (nodes.hasNext()) {
                        soapElement = (SOAPElement) nodes.next();
                        inicialQname = new QName(soapElement.getNamespaceURI(), soapElement.getLocalName());
                        explore(soapElement, env);
                    }
                }

            } catch (SOAPException e) {
                System.err.println(e);
            }
            System.out.println("\n Treated Outbound message:" + soapMessage.toString());
        } else {
            System.out.println("\nInbound message:");
        }
        return true;
    }


    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }


    private boolean explore(SOAPElement soapElement, SOAPEnvelope env) {
        QName subSequentQname = null;
        SOAPElement subSequentSOAPElement = null;
        String subSequentLocalName = null;
        if (soapElement != null && soapElement.getLocalName() != null) {
            /**/
            System.out.println(soapElement.getLocalName());
            if (soapElement.hasChildNodes()) {
                Iterator soapElementIterator = soapElement.getChildElements();
                while (soapElementIterator.hasNext()) {
                    Object nextElement = soapElementIterator.next();
                    if (nextElement instanceof TextImpl) continue;
                    subSequentSOAPElement = (SOAPElement) nextElement;
                    boolean test = explore(subSequentSOAPElement, env);

                    if (subSequentSOAPElement.getNamespaceURI() != null && subSequentSOAPElement.getLocalName() != null) {
                        subSequentLocalName =subSequentSOAPElement.getLocalName();
                        subSequentQname = new QName(subSequentSOAPElement.getNamespaceURI(), subSequentLocalName);
                        Name bodyName;
                        try {
                            bodyName = env.createName(subSequentLocalName);
                            soapElement.addChildElement(subSequentLocalName);
                            soapElement.removeChild(subSequentSOAPElement);
                        } catch (SOAPException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
                return false;
            } else {
                return true;
            }
        }


}

//<Payload xsi:type="ns4:XmlEntityPayload"> ->   <Payload xsi:type="ns4:XmlEntityPayload" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
// <ns5:Vessel/> -> <vessel>