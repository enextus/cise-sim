
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

import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebServiceClient(
        name = "CISEMessageService",
        targetNamespace = "http://www.cise.eu/accesspoint/service/v1/",
        wsdlLocation = "META-INF/wsdl/CISEMessageService.wsdl")
@HandlerChain(file = "/handlers.xml")

public class CISEMessageService
        extends Service {

    private static final URL CISEMESSAGESERVICE_WSDL_LOCATION;
    private static final WebServiceException CISEMESSAGESERVICE_EXCEPTION;
    private static final QName CISEMESSAGESERVICE_QNAME = new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            URL baseUrl = CISEMessageService.class.getClassLoader().getResource(".");
            url = new URL(baseUrl, "META-INF/wsdl/CISEMessageService.wsdl");


        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CISEMESSAGESERVICE_WSDL_LOCATION = url;
        CISEMESSAGESERVICE_EXCEPTION = e;
    }

    public CISEMessageService() {
        super(getWsdlLocation(), CISEMESSAGESERVICE_QNAME);
    }

    public CISEMessageService(WebServiceFeature... features) {
        super(getWsdlLocation(), CISEMESSAGESERVICE_QNAME, features);
    }

    public CISEMessageService(URL wsdlLocation) {
        super(wsdlLocation, CISEMESSAGESERVICE_QNAME);
    }

    public CISEMessageService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CISEMESSAGESERVICE_QNAME, features);
    }

    public CISEMessageService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CISEMessageService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns CISEMessageServiceSoapImpl
     */
    @WebEndpoint(name = "CISEMessageServiceSoapPort")
    public CISEMessageServiceSoapImpl getCISEMessageServiceSoapPort() {
        return super.getPort(new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageServiceSoapPort"), CISEMessageServiceSoapImpl.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns CISEMessageServiceSoapImpl
     */
    @WebEndpoint(name = "CISEMessageServiceSoapPort")
    public CISEMessageServiceSoapImpl getCISEMessageServiceSoapPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageServiceSoapPort"), CISEMessageServiceSoapImpl.class, features);
    }

    private static URL getWsdlLocation() {
        if (CISEMESSAGESERVICE_EXCEPTION != null) {
            throw CISEMESSAGESERVICE_EXCEPTION;
        }
        return CISEMESSAGESERVICE_WSDL_LOCATION;
    }

}