package eu.cise.dispatcher.soap;

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
import java.util.*;

@WebService(name = "WsHandler", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/")
@HandlerChain(file = "handlers.xml")
public class WsHandler implements SOAPHandler<SOAPMessageContext> {

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
                // Grab the header of the SOAP envelop
                SOAPPart soapPart = messageContext.getMessage().getSOAPPart();
                SOAPEnvelope env = soapPart.getEnvelope();
                SOAPBody soapBody = env.getBody();

                if (soapBody != null) {
                    SOAPElement soapElement = (SOAPElement) soapBody.getFirstChild().getFirstChild();
                    Iterator nodes = soapElement.getChildElements();
                    while (nodes.hasNext()) {
                        soapElement = (SOAPElement) nodes.next();
                        String localName = soapElement.getLocalName();
                        if ("Payload".equals(localName)) {
                            List<SOAPElement> newPayloadElements = new ArrayList<>();
                            Iterator childElements = soapElement.getChildElements();
                            while (childElements.hasNext()) {
                                SOAPElement payloadElement = (SOAPElement) childElements.next();
                                soapElement.removeChild(payloadElement);
                                newPayloadElements.add(payloadElement.setElementQName(new QName(payloadElement.getLocalName())));
                            }
                            soapElement.removeContents();
                            for (SOAPElement newPayloadElement : newPayloadElements) {
                                soapElement.addChildElement(newPayloadElement);
                            }
                        }
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

}


