package eu.cise.emulator.api;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherFactory;
import eu.cise.dispatcher.DispatcherType;
import eu.cise.emulator.DefaultEmulatorEngine;
import eu.cise.emulator.DefaultMessageProcessor;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.DefaultTemplateLoader;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import wiremock.org.apache.http.HttpHeaders;

import javax.ws.rs.core.MediaType;
import java.net.URL;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Examples on how to use WireMock with JUnit4.
 * This class uses a JUnit @Rule to create and destroy the WireMock server.
 *
 * @author Ivan Krizsan
 */
public class RestMessageReceiveTest extends AbstractTestBase {
    /* Constant(s): */

    /* Instance variable(s): */
    @Rule
    public WireMockRule mWireMockRule = new WireMockRule(HTTP_ENDPOINT_PORT);

    XmlMapper xmlmapperNoValidNoPretty= new DefaultXmlMapper.NotValidating();
    XmlMapper xmlmapperNoValidPretty= new DefaultXmlMapper.PrettyNotValidating();


    /**
     * Performs preparations before each test.
     */
    @Before
    public void setup() {
        initializeRestAssuredHttp();
    }

    /**
     * Test sending a HTTP request to the mock server that does not match the request
     * that the mock server expects.
     * This example shows how WireMock behaves when using a JUnit rule to create the
     * WireMock server.
     * <p>
     * Expected result: A response with the HTTP status not-found should be received and
     * a {@code VerificationException} should be thrown when the JUnit rule is evaluated,
     * after completion of the test.
     */
    @Test
    public void It_receive_signedRestCall_and_succesfullyValidate() {
        /*
         * Test HTTP mock expects one request to /wiremock/test with an Accept header
         * that has the value "application/json".
         * When having received such a request, it will return a response with
         * the HTTP status 200 and the header Content-Type with the value "text/plain".
         * The body of the response will be a string containing a greeting.
         */
        stubFor(
                get(urlEqualTo(BASE_PATH)).withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_XML))
                                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML)
                                .withBody("Hello World, we have a Houston!")
                        )
        );

        /*
         * Send the test-request and save the response so we can log information from it.
         * No verification of the response is undertaken, as a content-type that the mock
         * server is not expecting is deliberately sent and the .
         * This is done in order to examine the response in cases like this.
         */

        MessageStorage messageStorage = mock(MessageStorage.class);
        String messageStr = MessageBuilderUtil.TEST_MESSAGE_XML;


        ////*correlation:Disp-Sign where P= pretty V=Valid p=nonpretty or v=nonvalid: signature.fail:Pv-Pv,Pv-pv,pv-Pv  and sax.fail: PV-PV,pV-pV success:pv-pv
        Message messageToSign =xmlmapperNoValidNoPretty.fromXML(messageStr);

        SignatureService signatureService=makeSignatureService(xmlmapperNoValidNoPretty);
        messageToSign = signatureService.sign(messageToSign);
        String messageSignStr= xmlmapperNoValidNoPretty.toXML(messageToSign);

        MessageProcessor messageProcessor= makeMessageProcessor(xmlmapperNoValidNoPretty);
        TemplateLoader templateLoader= makeTemplateLoader();
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage, templateLoader, xmlmapperNoValidPretty, xmlmapperNoValidNoPretty);
        Acknowledgement response =messageAPI.receive(messageSignStr);
        verify(messageStorage).store(any());
        assertThat(response.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);

    }



    public MessageProcessor makeMessageProcessor(XmlMapper xmlMapper) {
        return new DefaultMessageProcessor(makeEmulatorEngine(xmlMapper));
    }

    private DefaultEmulatorEngine makeEmulatorEngine(XmlMapper xmlMapper) {
        return new DefaultEmulatorEngine(makeSignatureService(xmlMapper), makeDispatcher(), this.emuConfig);
    }


    public TemplateLoader makeTemplateLoader() {
        return new DefaultTemplateLoader(emuConfig);
    }


    public Dispatcher makeDispatcher() {
        DispatcherFactory dispatcherFactory = new DispatcherFactory();
        return dispatcherFactory.getDispatcher(this.emuConfig.dispatcherType(), this.xmlmapperNoValidPretty); //*correlation:Disp-Sign where P= pretty V=Valid p=nonpretty or v=nonvalid: signature.fail:Pv-Pv,Pv-pv,pv-Pv  and sax.fail: PV-PV,pV-pV success:pv-pv
    }

    public static SignatureService makeSignatureService(XmlMapper xmlMapper) {
        URL systemResource = ClassLoader.getSystemResource("");
        System.setProperty("conf.dir", systemResource.getPath());
        return newSignatureService(xmlMapper)
                .withKeyStoreName("keyStore.jks")
                .withKeyStorePassword("password")
                .withPrivateKeyAlias("apache.nodecx.eucise.cx")
                .withPrivateKeyPassword("password")
                .build();
    }

    EmuConfig emuConfig= new EmuConfig() {
        @Override
        public String participantId() { return "N/A"; }
        @Override
        public String endpointUrl() {return "N/A"; }
        @Override
        public String keyStoreFileName() {return "keyStore.jks"; }
        @Override
        public String keyStorePassword() {return "password"; }
        @Override
        public String privateKeyAlias() {return "apache.nodecx.eucise.cx"; }
        @Override
        public String privateKeyPassword() {return "password"; }
        @Override
        public String messageTemplateDir() {return "cise-emulator-assembly/src/main/resources/templates/messages";}
        @Override
        public boolean isDateValidationEnabled() {return false;}
        @Override
        public DispatcherType dispatcherType() {return DispatcherType.REST;}
    };
}