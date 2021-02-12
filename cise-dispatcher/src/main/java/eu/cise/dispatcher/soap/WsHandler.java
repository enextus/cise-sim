/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.cise.dispatcher.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.*;

import static javax.xml.ws.handler.MessageContext.MESSAGE_OUTBOUND_PROPERTY;

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

            if (!outboundProperty) {
                logger.info("SOAP Inbound message received");
                return true;
            }

            logger.info("SOAP Outbound message sent");


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


