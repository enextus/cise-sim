package eu.cise.emulator.api;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PriorityType;
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
    public static final String TEST_MESSAGE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
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

    public static final String TEST_MESSAGE_SOAP = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <S:Body>\n" +
            "        <ns5:send xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\"\n" +
            "                  xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\"\n" +
            "                  xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\"\n" +
            "                  xmlns:ns5=\"http://www.cise.eu/accesspoint/service/v1/\">\n" +
            "            <message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns4:PullResponse\">\n" +
            "            <CorrelationID>0b38a1de-ef53-4174-8c48-fe6216667887</CorrelationID>\n" +
            "    <CreationDateTime>2019-10-31T09:27:08.918Z</CreationDateTime>\n" +
            "    <MessageID>0b38a1de-ef53-4174-8c48-fe6216667887</MessageID>\n" +
            "    <Priority>Low</Priority>\n" +
            "    <RequiresAck>false</RequiresAck>\n" +
            "    <Sender>\n" +
            "        <SeaBasin>NorthSea</SeaBasin>\n" +
            "        <ServiceID>de.sim1-node01.vessel.pull.provider</ServiceID>\n" +
            "        <ServiceOperation>Pull</ServiceOperation>\n" +
            "        <ServiceRole>Provider</ServiceRole>\n" +
            "        <ServiceStatus>Online</ServiceStatus>\n" +
            "        <ServiceType>VesselService</ServiceType>\n" +
            "    </Sender>\n" +
            "    <Recipient>\n" +
            "        <SeaBasin>NorthSea</SeaBasin>\n" +
            "        <ServiceID>de.sim2-node01.vessel.pull.consumer</ServiceID>\n" +
            "        <ServiceOperation>Pull</ServiceOperation>\n" +
            "        <ServiceRole>Consumer</ServiceRole>\n" +
            "        <ServiceStatus>Online</ServiceStatus>\n" +
            "        <ServiceType>VesselService</ServiceType>\n" +
            "    </Recipient>\n" +
            "    <Payload xsi:type=\"ns4:XmlEntityPayload\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "        <InformationSecurityLevel>NonClassified</InformationSecurityLevel>\n" +
            "        <InformationSensitivity>Green</InformationSensitivity>\n" +
            "        <IsPersonalData>false</IsPersonalData>\n" +
            "        <Purpose>NonSpecified</Purpose>\n" +
            "        <EnsureEncryption>false</EnsureEncryption>\n" +
            "        <Vessel>\n" +
            "            <IMONumber>7710525</IMONumber>\n" +
            "        </Vessel>\n" +
            "    </Payload>\n" +
            "    <Reliability>\n" +
            "        <RetryStrategy>NoRetry</RetryStrategy>\n" +
            "    </Reliability>\n" +
            "    <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
            "        <SignedInfo>\n" +
            "            <CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
            "            <SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>\n" +
            "            <Reference URI=\"\">\n" +
            "                <Transforms>\n" +
            "                    <Transform Algorithm=\"http://www.w3.org/TR/1999/REC-xslt-19991116\">\n" +
            "                        <xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:s=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
            "                            <xsl:strip-space elements=\"*\"/>\n" +
            "                            <xsl:output indent=\"false\" method=\"xml\" omit-xml-declaration=\"yes\"/>\n" +
            "                            <xsl:template match=\"*[not(self::s:Signature)]\">\n" +
            "                                <xsl:element name=\"{local-name()}\">\n" +
            "                                    <xsl:apply-templates select=\"*|text()\"/>\n" +
            "                                </xsl:element>\n" +
            "                            </xsl:template>\n" +
            "                            <xsl:template match=\"s:Signature\"/>\n" +
            "                        </xsl:stylesheet>\n" +
            "                    </Transform>\n" +
            "                    <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n" +
            "                </Transforms>\n" +
            "                <DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>\n" +
            "                <DigestValue>YqXSO6jRxBLO+siVbej3LbDZTtw=</DigestValue>\n" +
            "            </Reference>\n" +
            "        </SignedInfo>\n" +
            "        <SignatureValue>zvZZlfSl9G0t3p8UtSbIg1IUJH+xUdJ6xgWn1QAGv/1bRfXLB+lXSFoevS0Jpzbo8EbENxNsEY4n\n" +
            "JDXpUWcTrONkylUx+6sdjaDha7QU6xSbYvHM3g3cok9ErN0tiZmBr2lu0MDT1Qx9wOYsG7UkISgt\n" +
            "ts3Hjp+NhpLoRzZ+SIhDIMqEsn7Ob9T2E3HIqveWZpfMkAEdoEjAGoA4ZTvUkKQOvCqNB05XzwKG\n" +
            "hb0oopC0nrIZMRqxrpg5s12d9mXH3mBAbmBArIFgzt0+zEoKwDvOzJ51+jgzvU7z4W6S+0pNvDB5\n" +
            "We/nkOi4HiTAciX1PRJeIgHoxNmZsP7phcVwYw==</SignatureValue>\n" +
            "        <KeyInfo>\n" +
            "            <X509Data>\n" +
            "                <X509SubjectName>C=cx, DC=eucise, O=nodecx, OU=Participants, CN=cisesim1-nodecx.nodecx.eucise.cx</X509SubjectName>\n" +
            "                <X509Certificate>MIIEMDCCAxigAwIBAgIIPqE2ueIVxugwDQYJKoZIhvcNAQELBQAwPTEdMBsGA1UEAwwUc2lnbmlu\n" +
            "Zy1jYS5ldWNpc2UuY3gxDzANBgNVBAoMBmV1Y2lzZTELMAkGA1UEBhMCY3gwHhcNMTkxMDE1MTQw\n" +
            "MDQyWhcNMjkwNTMwMTY1ODEwWjB4MSkwJwYDVQQDDCBjaXNlc2ltMS1ub2RlY3gubm9kZWN4LmV1\n" +
            "Y2lzZS5jeDEVMBMGA1UECwwMUGFydGljaXBhbnRzMQ8wDQYDVQQKDAZub2RlY3gxFjAUBgoJkiaJ\n" +
            "k/IsZAEZFgZldWNpc2UxCzAJBgNVBAYTAmN4MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKC\n" +
            "AQEA617q2PmVZjSgQ2KKk66vGN7V8UQBs1bvWhlOdIGLTlsaPgxILE8hILDpFZjc40Ixdhx0EHhK\n" +
            "PcKakdJTYADyJ8xULgR7YN4V/BMkN2w/LTKKy7T4YEd+xg80XDH8PMpC0hB/hlVqB+xgYQzgyRRP\n" +
            "HQhiQllS8Im7agEDm7otJ4a2352nN+8IfjEHRW7eQSnwMKJ/gTyyYf+nYgA0dgj5BBuVJXRvUf5y\n" +
            "Js4Qe+eX13/pUp/xKElThLBmUh63VHorkyEXYQlfM58FkKRBZA3GWKF8Pj57vQArebipMLccuoE8\n" +
            "PJ2vvxcj193A5eCgm2PL4qThTZdk+PXHalCRmg/1twIDAQABo4H4MIH1MAwGA1UdEwEB/wQCMAAw\n" +
            "HwYDVR0jBBgwFoAU1ZKIVkrBZRLVTg6yR90LNU/YnxkwSQYIKwYBBQUHAQEEPTA7MDkGCCsGAQUF\n" +
            "BzABhi1odHRwOi8vZWpiY2E6ODA4MC9lamJjYS9wdWJsaWN3ZWIvc3RhdHVzL29jc3AwKwYDVR0R\n" +
            "BCQwIoIgY2lzZXNpbTEtbm9kZWN4Lm5vZGVjeC5ldWNpc2UuY3gwHQYDVR0lBBYwFAYIKwYBBQUH\n" +
            "AwIGCCsGAQUFBwMBMB0GA1UdDgQWBBQAkWNAOjbTj6r9n0eRdhgMPZDPZzAOBgNVHQ8BAf8EBAMC\n" +
            "AvwwDQYJKoZIhvcNAQELBQADggEBAMl9OBcA8aGiLjSGABbkAkT9PU9c08095gETjNGJRNodc2Fg\n" +
            "QRWBUj+TwPrPScpPUqu+bl7yEulo2JBQ55iaIKUkrdppxVK1BoZjsoaPr/s9eZpJ2kcKQJMyaJY/\n" +
            "Gyq95cPxyW8ZVuH+Ry5UeaozoaHE+Mhj8DRMaQNup/4E4nGI2bcfcw/W8E8pmg22T2Im0hygrb2U\n" +
            "veg8f0nNBUb1wgCI3W2iwYs5OaCnarTE2+tGK4XuYand6ITuZ3wKg5/4yuFjzRV4k7AzXFh66lIM\n" +
            "s8tpof3UKFfL1qknAlWfJvDVKfrpzU/c0I0WmryquNUMAmm7OpIn+N9pddXN0T2US7o=</X509Certificate>\n" +
            "            </X509Data>\n" +
            "        </KeyInfo>\n" +
            "    </Signature>\n" +
            "    <ResultCode>Success</ResultCode>\n" +
            "            </message>\n" +
            "        </ns5:send>\n" +
            "    </S:Body>\n" +
            "</S:Envelope>";


    public static String create() {
        return TEST_MESSAGE_XML;
    }

    public static String createMessageString() {
        Message message = createMessage();
        DefaultXmlMapper defaultXmlMapper = new DefaultXmlMapper();
        return defaultXmlMapper.toXML(message);
    }

    public static Message createMessage() {
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

    public static Acknowledgement createAcknowledgeMessage() {
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