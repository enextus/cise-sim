package eu.cise.dispatcher.soap;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.junit.Rule;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;


public class WsHandlerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089); // No-args constructor defaults to port 8089


    @Test
    public void exampleTest() {
        stubFor(get(urlEqualTo("/api/messages")) //localhost:8089/api/messages
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
                                "    <CorrelationID>fad3536e-7d52-4087-8c0c-8e48f3b5ee87</CorrelationID>\n" +
                                "    <CreationDateTime>2019-10-29T10:41:57.050Z</CreationDateTime>\n" +
                                "    <MessageID>d9553351-518d-4eda-b54d-0835d3bcf43e</MessageID>\n" +
                                "    <Priority>Low</Priority>\n" +
                                "    <RequiresAck>false</RequiresAck>\n" +
                                "    <Sender>\n" +
                                "        <ServiceID>cx.simlsa1-nodecx.vessel.push.provider</ServiceID>\n" +
                                "    </Sender>\n" +
                                "    <Recipient>\n" +
                                "        <ServiceID>cx.simlsa2-nodecx.vessel.push.consumer</ServiceID>\n" +
                                "    </Recipient>\n" +
                                "    <AckCode>AuthenticationError</AckCode>\n" +
                                "    <AckDetail>Message signature not validated: Signature failed core validation.\n" +
                                "Signature validation status: false\n" +
                                "ref[0] validity status: true\n" +
                                "</AckDetail>\n" +
                                "</ns4:Acknowledgement>")));
        CISEMessageServiceSoapImpl service = new CISEMessageService().getCISEMessageServiceSoapPort();
        WsHandler test = new WsHandler();


//        JAXBUtils.unmarshalFromXmlDocument();

        try {
            URL baseUrl = CISEMessageService.class.getClassLoader().getResource(".");
            URL url = new URL(baseUrl, "META-INF/wsdl/CISEMessageService.wsdl");

        } catch (MalformedURLException murl) {
            throw new RuntimeException(murl);
        }

        Vessel vessel = new Vessel();
        vessel.setCallSign("coucou");
        Push pushMessage = newPush()
                .id("messageId")
                .correlationId("correlation-id")
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .isRequiresAck(true)
                .informationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED)
                .informationSensitivity(InformationSensitivityType.GREEN)
                .setEncryptedPayload("false")
                .isPersonalData(false)
                .purpose(PurposeType.NON_SPECIFIED)
                .sender(newService().id("service-id").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("recipient-id").operation(ServiceOperationType.PUSH).build())
                .addEntity(vessel)
                .build();

        Acknowledgement ack = service.send(pushMessage);


        verify(postRequestedFor(urlMatching("/api/messages"))
                .withRequestBody(matching(".*<message>1234</message>.*"))
                .withHeader("Content-Type", notMatching("application/xml")));
    }


}