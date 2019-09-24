package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CiseMessageResourceTest {
    private static MessageAPI messageAPI = mock(MessageAPI.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new CiseMessageResource(messageAPI))
            .bootstrapLogging(false)
            .build();

    @Ignore //TODO: solve the response
    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {

        try {
            Response response = resources.target("/api/cisemessage")
                    .request()
                    .post(Entity.entity((MessageBuilder.create()), MediaType.APPLICATION_XML_TYPE));
            assertThat(response.getStatus()).isEqualTo(201);

        } catch (Exception e) {
            // do nothing
        }
    }
}

  class MessageBuilder{
      static final String EXAMPLAR_TEMPLATE_MESSAGE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
              "<ns4:Push xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
              "    <CorrelationID>476d949d-5aa4-44cc-8e20-c1a2288fe098</CorrelationID>\n" +
              "    <CreationDateTime>2019-09-20T07:49:56.323Z</CreationDateTime>\n" +
              "    <MessageID>f4f07849-4cb5-4913-a1ec-2e5e66685714</MessageID>\n" +
              "    <Priority>High</Priority>\n" +
              "    <RequiresAck>true</RequiresAck>\n" +
              "    <Sender>\n" +
              "        <SeaBasin>NorthSea</SeaBasin>\n" +
              "        <ServiceID>cx.simlsa2-nodecx.action.push.provider</ServiceID>\n" +
              "        <ServiceOperation>Push</ServiceOperation>\n" +
              "        <ServiceRole>Provider</ServiceRole>\n" +
              "        <ServiceStatus>Online</ServiceStatus>\n" +
              "        <ServiceType>ActionService</ServiceType>\n" +
              "    </Sender>\n" +
              "    <Payload xsi:type=\"ns4:XmlEntityPayload\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
              "        <InformationSecurityLevel>NonClassified</InformationSecurityLevel>\n" +
              "        <InformationSensitivity>Red</InformationSensitivity>\n" +
              "        <IsPersonalData>false</IsPersonalData>\n" +
              "        <Purpose>FisheriesMonitoring</Purpose>\n" +
              "        <EnsureEncryption>false</EnsureEncryption>\n" +
              "        <Action>\n" +
              "            <Identifier>\n" +
              "                <GeneratedBy xsi:type=\"ns3:FormalOrganization\" xmlns:ns3=\"http://www.cise.eu/datamodel/v1/entity/organization/\">\n" +
              "                    <LegalName>A1</LegalName>\n" +
              "\t\t\t\t</GeneratedBy>\n" +
              "                <UUID>65d2a363-74f6-415a-99d1-af7f6cd1525c</UUID>\n" +
              "\t\t\t</Identifier>\n" +
              "            <ImpliedRiskRel>\n" +
              "                <Risk>\n" +
              "                    <Identifier>\n" +
              "                        <GeneratedBy xsi:type=\"ns3:FormalOrganization\" xmlns:ns3=\"http://www.cise.eu/datamodel/v1/entity/organization/\">\n" +
              "                            <LegalName>A1</LegalName>\n" +
              "\t\t\t\t\t\t</GeneratedBy>\n" +
              "                        <UUID>6afa2072-2237-44b3-b5c0-1bb895e00c8c</UUID>\n" +
              "\t\t\t\t\t</Identifier>\n" +
              "                    <RiskType>IllegalFishing</RiskType>\n" +
              "\t\t\t\t</Risk>\n" +
              "\t\t\t</ImpliedRiskRel>\n" +
              "            <InvolvedObjectRel>\n" +
              "                <Object xsi:type=\"ns3:Vessel\" xmlns:ns3=\"http://www.cise.eu/datamodel/v1/entity/vessel/\">\n" +
              "                    <MMSI>636014356</MMSI>\n" +
              "\t\t\t\t</Object>\n" +
              "\t\t\t</InvolvedObjectRel>\n" +
              "            <ActionType>Inspection</ActionType>\n" +
              "            <Mission>OPR</Mission>\n" +
              "            <Priority>High</Priority>\n" +
              "\t\t</Action>\n" +
              "    </Payload>\n" +
              "    <DiscoveryProfiles>\n" +
              "        <ServiceOperation>Push</ServiceOperation>\n" +
              "        <ServiceRole>Consumer</ServiceRole>\n" +
              "        <ServiceType>ActionService</ServiceType>\n" +
              "    </DiscoveryProfiles>\n" +
              "</ns4:Push>";


    static String create(){return EXAMPLAR_TEMPLATE_MESSAGE_XML;}
}