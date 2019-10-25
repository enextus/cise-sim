package eu.cise.dispatcher.soap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

@WebService(name = "WsHandler", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/")
@HandlerChain(file = "handlers.xml")
public class WsHandler implements SOAPHandler<SOAPMessageContext> {
    private static final String NAMESPACE_URI = "default";
    private final String VALID_PROPERTY = "RANDOM";
    private static Document thisDocument;

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
        Boolean outboundProperty = (Boolean)  messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
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

                    SOAPBody soapBody = soapMessage.getSOAPBody();
                    QName inicialQname = null;
                    if (soapBody != null) {
                        Iterator nodes = soapBody.getChildElements();//getChildNodes();
                        org.w3c.dom.Node node = null;
                        while (nodes.hasNext()){
                            node = (org.w3c.dom.Node) nodes.next();
                                Explore( node);
                                inicialQname = new QName(node.getNamespaceURI(), node.getLocalName());
                        }
                    }

                } catch ( SOAPException e) {
                    System.err.println(e);
                }
                System.out.println("\n Treated Outbound message:"+soapMessage.toString());
            } else {
                System.out.println("\nInbound message:");
            }
            return true;
        }



        public boolean handleFault (SOAPMessageContext messageContext)
        {
            return true;
        }

        @Override
        public void close (MessageContext context){

        }




    private void Explore(org.w3c.dom.Node node) {
        QName subSequentQname = null;
        if (node.getLocalName() != null) {

            System.out.println(node.getLocalName());
            NodeList nodes;
            nodes = node.getChildNodes();
            org.w3c.dom.Node subSequentNode = null;
            for (int i = 0; i < nodes.getLength(); i++) {
                subSequentNode = nodes.item(i);
                Explore( subSequentNode);
                if (subSequentNode.getNamespaceURI()!=null  && subSequentNode.getLocalName()!= null){
                    String subSequentLocalName = subSequentNode.getLocalName();
                    subSequentQname = new QName(subSequentNode.getNamespaceURI(), subSequentLocalName);
                    subSequentNode.normalize();
                    subSequentNode.setPrefix("");
                    subSequentNode.setTextContent("");
                    subSequentNode.setNodeValue("");
                    String markup= "http://www.cise.eu/servicemodel/v1/message/";
                    ////Element getLastTradePrice = thisDocument.createElementNS(markup, subSequentLocalName);
                    //Element tag = thisDocument.createElement(subSequentLocalName);
                    ////symbol.setAttribute("foo", "bar");
                    //subSequentNode.getParentNode().appendChild(tag);
                    // subSequentNode.getParentNode().removeChild(subSequentNode);
                    System.out.println(markup + " : " +subSequentLocalName);
                }
            }
        }
    }

}

//<Payload xsi:type="ns4:XmlEntityPayload"> ->   <Payload xsi:type="ns4:XmlEntityPayload" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
// <ns5:Vessel/> -> <vessel>