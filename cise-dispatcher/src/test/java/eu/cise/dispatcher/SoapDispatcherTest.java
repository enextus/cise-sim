package eu.cise.dispatcher;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.cargo.Cargo;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import org.junit.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;


public class SoapDispatcherTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089); // No-args constructor defaults to port 8089

    public static SignatureService makeSignatureService() {
        System.setProperty("conf.dir", "/home/longama/IdeaProjects/cise-emu/cise-dispatcher/src/main/resources/");
        return newSignatureService()
                .withKeyStoreName("cisesim1-nodecx.jks")
                .withKeyStorePassword("password")
                .withPrivateKeyAlias("cisesim1-nodecx.nodecx.eucise.cx")
                .withPrivateKeyPassword("password")
                .build();
    }


    @Before
    public void before() {
//        wireMockRule.stubFor(get(urlEqualTo("/api/messages")) //localhost:8089/api/messages
//                .withHeader("Accept", equalTo("text/html"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "text/xml")
//                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
//                                "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
//                                "    <CorrelationID>fad3536e-7d52-4087-8c0c-8e48f3b5ee87</CorrelationID>\n" +
//                                "    <CreationDateTime>2019-10-29T10:41:57.050Z</CreationDateTime>\n" +
//                                "    <MessageID>d9553351-518d-4eda-b54d-0835d3bcf43e</MessageID>\n" +
//                                "    <Priority>Low</Priority>\n" +
//                                "    <RequiresAck>false</RequiresAck>\n" +
//                                "    <Sender>\n" +
//                                "        <ServiceID>cx.simlsa1-nodecx.vessel.push.provider</ServiceID>\n" +
//                                "    </Sender>\n" +
//                                "    <Recipient>\n" +
//                                "        <ServiceID>cx.simlsa2-nodecx.vessel.push.consumer</ServiceID>\n" +
//                                "    </Recipient>\n" +
//                                "    <AckCode>AuthenticationError</AckCode>\n" +
//                                "    <AckDetail>Message signature not validated: Signature failed core validation.\n" +
//                                "Signature validation status: false\n" +
//                                "ref[0] validity status: true\n" +
//                                "</AckDetail>\n" +
//                                "</ns4:Acknowledgement>")));

        try {
            String fileString = new String(Files.readAllBytes(Paths.get("/home/longama/IdeaProjects/cise-emu/cise-dispatcher/src/test/resources/META-INF/wsdl/CISEMessageService.wsdl")), StandardCharsets.UTF_8);
//            FileReader fileReader = new FileReader("/home/longama/IdeaProjects/cise-emu/cise-dispatcher/src/test/resources/META-INF/wsdl/CISEMessageService.wsdl");
//            fileReader.

            StubMapping stubMapping = wireMockRule.stubFor(get(anyUrl()) //localhost:8089/api/messages
                    .withHeader("Accept", containing("text/html"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "text/xml")
                            .withBody(fileString)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void after() {
    }

    @Ignore
    @Test
    public void it_sends_the_message_to_the_specified_address_with_success() {


        Dispatcher soapDispatcher = new SoapDispatcher();

        Message msg = buildMessage();
        SignatureService signatureService = makeSignatureService();
        Message signedMessage = signatureService.sign(msg);

//        String soapEndpointDestination = "http://192.168.42.37:8180/eucise-com-services-web/CISEMessageService?wsdl";
        String soapEndpointDestination = "http://localhost:8089/api/messages?wsdl";
        DispatchResult sendResult = soapDispatcher.send(signedMessage, soapEndpointDestination);
        Acknowledgement ack = sendResult.getResult();
        System.out.println(ack);

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }

    private Message buildMessage() {
        Vessel vessel = new Vessel();
        vessel.setIMONumber(123L);
        vessel.setCallSign("callSign");
        Cargo cargo = new Cargo();
        List<Entity> entities = new ArrayList<>();
        entities.add(vessel);
        entities.add(cargo);

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
                .sender(newService().id("cx.cisesim1-nodecx.vessel.push.provider").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("cx.cisesim2-nodecx.vessel.push.consumer").operation(ServiceOperationType.PUSH).build())
                .addEntities(entities)
                .build();
        return pushMessage;
    }

}
