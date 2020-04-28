package eu.cise.dispatcher.soap;

import static javax.xml.ws.handler.MessageContext.MESSAGE_OUTBOUND_PROPERTY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(name = "WsHandler", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/")
@HandlerChain(file = "handlers.xml")
public class WsHandler implements SOAPHandler<SOAPMessageContext> {

    @Resource
    WebServiceContext ctx;
    private Logger logger = LoggerFactory.getLogger(WsHandler.class);

    @WebMethod()
    public String getProperty(String propertyName) {
        return (String) ctx.getMessageContext().get(propertyName);
    }

    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    public boolean handleMessage(SOAPMessageContext messageContext) {
        try {
            Boolean outboundProperty = (Boolean) messageContext.get(MESSAGE_OUTBOUND_PROPERTY);

            logger.info("Outbound message: {}", messageContext.getMessage());

            if (!outboundProperty) {
                logger.info("Inbound message: {}", messageContext.getMessage());
                return true;
            }

            SOAPBody soapBody = extractSoapBodyFrom(messageContext);

            if (soapBody == null)
                return true;

            Iterator<Node> nodes = extractSoapElements(soapBody);

            while (nodes.hasNext()) {
                SOAPElement soapElement = (SOAPElement) nodes.next();

                if (!soapElement.getLocalName().equals("Payload"))
                    continue;

                possiblyFixNameSpace(soapElement);
            }

        } catch (SOAPException e) {
            logger.error("Error getting the soap message.", e);
        }

        return true;
    }

    /**
     * TODO give a better name to this method to be more expressive and make the reader understand
     * what it does.
     *
     * @param soapBody the soap message body
     * @return a list of node elements part of the message body
     */
    private Iterator<Node> extractSoapElements(SOAPBody soapBody) {
        return ((SOAPElement) soapBody.getFirstChild().getFirstChild()).getChildElements();
    }

    private SOAPBody extractSoapBodyFrom(SOAPMessageContext messageContext) throws SOAPException {
        return messageContext.getMessage().getSOAPPart().getEnvelope().getBody();
    }

    /**
     * TODO Understand better this code: it seems delete the content of the payload re-adding the
     * same content without namespaces
     *
     * @param soapElement the payload soap element
     * @throws SOAPException thrown in case of an error.
     */
    private void possiblyFixNameSpace(SOAPElement soapElement) throws SOAPException {
        List<SOAPElement> newPayloadElements = new ArrayList<>();

        Iterator<Node> childElements = soapElement.getChildElements();

        while (childElements.hasNext()) {
            SOAPElement payloadElement = (SOAPElement) childElements.next();

            soapElement.removeChild(payloadElement);

            newPayloadElements.add(
                payloadElement.setElementQName(new QName(payloadElement.getLocalName()))
            );
        }

        soapElement.removeContents();

        for (SOAPElement newPayloadElement : newPayloadElements) {
            soapElement.addChildElement(newPayloadElement);
        }
    }

    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }

}


