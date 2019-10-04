package eu.cise.emulator.api.resources;

import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.eucise.helpers.AckBuilder;
import eu.eucise.xml.DefaultXmlMapper;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;

public class MessageBuilderUtil {
    static final String TEST_MESSAGE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
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


    static String create() {
        return TEST_MESSAGE_XML;
    }

    static String createMessageString() {
        Message message = createMessage();
        DefaultXmlMapper defaultXmlMapper = new DefaultXmlMapper();
        return defaultXmlMapper.toXML(message);
    }

    static Message createMessage() {
        Service service = newService().type(VESSEL_SERVICE).build();
        service.setServiceID("serviceID");
        service.setServiceType(ServiceType.VESSEL_SERVICE);
        service.setServiceOperation(ServiceOperationType.PUSH);


        return newPush()
                .id("sampleMessageId")
                .sender(service)
                .priority(PriorityType.HIGH)
                .creationDateTime(Date.from(Instant.now()))
                .build();
    }

    static Acknowledgement createAcknowledgeMessage() {
        Message message = createMessage();
        AcknowledgementType acknowledgementType;
        String acknowledgementDetail;

        // define the acknowledgementType
        if (!message.getSender().getServiceType().equals(VESSEL_SERVICE)) {
            acknowledgementType = AcknowledgementType.SERVICE_TYPE_NOT_SUPPORTED;
            acknowledgementDetail = "Supported service type is " + message.getSender().getServiceType().value();
        } else {
            acknowledgementType = AcknowledgementType.SUCCESS;
            acknowledgementDetail = "Message delivered";
        }

        // build the acknowledgement
        AckBuilder ackBuilder = newAck()
                .id(UUID.randomUUID().toString())
                .sender(newService()
                        .id(message.getSender().getServiceID())
                        .operation(ServiceOperationType.ACKNOWLEDGEMENT))
                .creationDateTime(java.util.Date.from(java.time.ZonedDateTime.now(ZoneId.of("UTC")).toInstant()))
                .informationSecurityLevel(message.getPayload().getInformationSecurityLevel())
                .informationSensitivity(message.getPayload().getInformationSensitivity())
                .purpose(message.getPayload().getPurpose())
                .priority(message.getPriority())
                .ackCode(acknowledgementType)
                .ackDetail(acknowledgementDetail)
                .isRequiresAck(false);

        Acknowledgement acknowledgement = ackBuilder.build();
        return acknowledgement;
    }

}