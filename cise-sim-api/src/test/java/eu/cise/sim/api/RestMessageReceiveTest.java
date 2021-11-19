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

package eu.cise.sim.api;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import eu.cise.dispatcher.DispatcherFactory;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.*;
import eu.cise.sim.templates.DefaultTemplateLoader;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import wiremock.org.apache.http.HttpHeaders;

import javax.ws.rs.core.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static org.assertj.core.api.Assertions.assertThat;

public class RestMessageReceiveTest {

    @Rule
    public WireMockRule mWireMockRule = new WireMockRule(8123);
    private final SimConfig simConfig = new SimConfig() {
        @Override
        public String simulatorName() {
            return "N/A";
        }

        @Override
        public String destinationUrl() {
            return "N/A";
        }

        @Override
        public String keyStoreFileName() {
            return "cisesim-nodeex.jks";
        }

        @Override
        public String keyStorePassword() {
            return "cisesim";
        }

        @Override
        public String privateKeyAlias() {
            return "cisesim-nodeex.nodeex.eucise.ex";
        }

        @Override
        public String privateKeyPassword() {
            return "cisesim";
        }

        @Override
        public String messageTemplateDir() {
            return "templates/messages";
        }

        @Override
        public String messageHistoryDir() { return "msghistory"; }

        @Override
        public int guiMaxThMsgs() {
            return 10;
        }

        @Override
        public DispatcherType destinationProtocol() {
            return DispatcherType.REST;
        }

        @Override
        public String proxyHost() {
            return "";
        }

        @Override
        public String proxyPort() {
            return "";
        }

        @Override
        public boolean showIncident() {
            return true;
        }

        @Override
        public String discoverySender() {
            return "";
        }

        @Override
        public String discoveryServiceType() {
            return "";
        }

        @Override
        public String discoveryServiceOperation() {
            return "";
        }
    };
    private final XmlMapper xmlMapperNoValidNoPretty = new DefaultXmlMapper.NotValidating();
    private final XmlMapper xmlMapperNoValidPretty = new DefaultXmlMapper.PrettyNotValidating();

    private SignatureService makeSignatureService(XmlMapper xmlMapper) {
        return newSignatureService(xmlMapper)
            .withKeyStoreName("cisesim-nodeex.jks")
            .withKeyStorePassword("cisesim")
            .withPrivateKeyAlias("cisesim-nodeex.nodeex.eucise.ex")
            .withPrivateKeyPassword("cisesim")
            .build();
    }

    /**
     * Performs preparations before each test.
     */
    @Before
    public void setup() {
        RestAssured.reset();
        RestAssured.port = 8123;
    }

    /**
     * Test sending a HTTP request to the mock server that does not match the request that the mock
     * server expects. This example shows how WireMock behaves when using a JUnit rule to create the
     * WireMock server.
     * <p>
     * Expected result: A response with the HTTP status not-found should be received and a {@code
     * VerificationException} should be thrown when the JUnit rule is evaluated, after completion of
     * the test.
     */
    @Test
    public void it_receives_signed_rest_call_and_successfully_validates_it() {
        /*
         * Test HTTP mock expects one request to /wiremock/test with an Accept header
         * that has the value "application/json".
         * When having received such a request, it will return a response with
         * the HTTP status 200 and the header Content-Type with the value "text/plain".
         * The body of the response will be a string containing a greeting.
         */
        stubFor(
            get(urlEqualTo("/api/messages"))
                .withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_XML))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML)
                    .withBody("Hello World, we have a Houston!")
                )
        );


        String messageStr = MessageBuilderUtil.TEST_MESSAGE_XML;

        Message messageToSign = xmlMapperNoValidNoPretty.fromXML(messageStr);

        SignatureService signatureService = makeSignatureService(xmlMapperNoValidNoPretty);

        messageToSign = signatureService.sign(messageToSign);

        String messageSignStr = xmlMapperNoValidNoPretty.toXML(messageToSign);

        MessageProcessor messageProcessor = makeMessageProcessor(xmlMapperNoValidNoPretty);

        TemplateLoader templateLoader = makeTemplateLoader();

        MessageAPI messageAPI = new DefaultMessageAPI(
                messageProcessor,
                templateLoader,
                xmlMapperNoValidPretty,
                xmlMapperNoValidNoPretty);

        ResponseApi<Acknowledgement> response = messageAPI.receive(messageToSign);


        assertThat(response.getResult().getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);

    }

    private MessageProcessor makeMessageProcessor(XmlMapper xmlMapper) {
        return new DefaultMessageProcessor(makeSimEngine(xmlMapper));
    }

    private DefaultSimEngine makeSimEngine(XmlMapper xmlMapper) {
        return new DefaultSimEngine(makeSignatureService(xmlMapper), makeDispatcher(),
            this.simConfig);
    }

    private TemplateLoader makeTemplateLoader() {
        return new DefaultTemplateLoader(simConfig);
    }

    private Dispatcher makeDispatcher() {
        DispatcherFactory dispatcherFactory = new DispatcherFactory();
        return dispatcherFactory
            .getDispatcher(this.simConfig.destinationProtocol(), this.xmlMapperNoValidPretty);
    }
}