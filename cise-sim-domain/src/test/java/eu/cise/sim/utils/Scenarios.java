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

package eu.cise.sim.utils;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

public class Scenarios {

    private static final String ASYNC_ACK_MSG_SUCCEEDED =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n"
            +
            "    <CorrelationID>c1d19e40-e4db-460a-a924-3ada68bb260b</CorrelationID>\n" +
            "    <CreationDateTime>2019-08-16T10:20:38.011Z</CreationDateTime>\n" +
            "    <MessageID>d668083a-5e20-479a-88ee-61c81393accf</MessageID>\n" +
            "    <Priority>Low</Priority>\n" +
            "    <RequiresAck>false</RequiresAck>\n" +
            "    <Sender>\n" +
            "        <ServiceID>cx.simlsa2-nodecx.persondocument.subscribe.consumer</ServiceID>\n" +
            "        <ServiceOperation>Acknowledgement</ServiceOperation>\n" +
            "        <Participant>\n" +
            "            <Id>eu.eucise.cx.simlsa2-nodecx</Id>\n" +
            "            <Name>Simulator LSA 2</Name>\n" +
            "            <Description>Second simulator LSA</Description>\n" +
            "            <ClassificationLevel>Unclassified</ClassificationLevel>\n" +
            "            <EndpointUrl>http://192.168.42.37:8380/sim-LSA/CISEMessageService</EndpointUrl>\n"
            +
            "            <EndpointType>SOAP</EndpointType>\n" +
            "            <ProvidedServicesIds>cx.simlsa2-nodecx.vessel.push.consumer</ProvidedServicesIds>\n"
            +
            "            <ProvidedServicesIds>cx.simlsa2-nodecx.persondocument.subscribe.consumer</ProvidedServicesIds>\n"
            +
            "            <Gateway>\n" +
            "                <Id>eu.eucise.cx.nodecx</Id>\n" +
            "            </Gateway>\n" +
            "            <Owner>JRC</Owner>\n" +
            "            <PointOfContact>\n" +
            "                <Name>Jesus</Name>\n" +
            "            </PointOfContact>\n" +
            "            <AreasOfInterest>BalticSea</AreasOfInterest>\n" +
            "            <AreasOfInterest>NorthSea</AreasOfInterest>\n" +
            "            <Communities>GeneralLawEnforcement</Communities>\n" +
            "            <Communities>MarineEnvironment</Communities>\n" +
            "            <Functions>Safety</Functions>\n" +
            "            <MemberState>CX</MemberState>\n" +
            "        </Participant>\n" +
            "    </Sender>\n" +
            "    <Recipient>\n" +
            "        <ServiceID>cx.simlsa1-nodecx.persondocument.subscribe.provider</ServiceID>\n" +
            "        <ServiceOperation>Acknowledgement</ServiceOperation>\n" +
            "        <Participant>\n" +
            "            <Id>eu.eucise.cx.simlsa1-nodecx</Id>\n" +
            "            <Name>Simulator LSA 1</Name>\n" +
            "            <Description>First simulator LSA</Description>\n" +
            "            <ClassificationLevel>Unclassified</ClassificationLevel>\n" +
            "            <EndpointUrl>http://192.168.42.37:8280/sim-LSA/CISEMessageService</EndpointUrl>\n"
            +
            "            <EndpointType>SOAP</EndpointType>\n" +
            "            <ProvidedServicesIds>cx.simlsa1-nodecx.vessel.push.provider</ProvidedServicesIds>\n"
            +
            "            <ProvidedServicesIds>cx.simlsa1-nodecx.vessel.pull.consumer</ProvidedServicesIds>\n"
            +
            "            <ProvidedServicesIds>cx.simlsa1-nodecx.persondocument.subscribe.provider</ProvidedServicesIds>\n"
            +
            "            <Gateway>\n" +
            "                <Id>eu.eucise.cx.nodecx</Id>\n" +
            "            </Gateway>\n" +
            "            <Owner>JRC</Owner>\n" +
            "            <PointOfContact>\n" +
            "                <Name>Jesus</Name>\n" +
            "            </PointOfContact>\n" +
            "            <AreasOfInterest>BalticSea</AreasOfInterest>\n" +
            "            <AreasOfInterest>Mediterranean</AreasOfInterest>\n" +
            "            <Communities>Customs</Communities>\n" +
            "            <Communities>MaritimeSafetySecurity</Communities>\n" +
            "            <Functions>BorderMonitoring</Functions>\n" +
            "            <Functions>BorderOperation</Functions>\n" +
            "            <MemberState>CX</MemberState>\n" +
            "        </Participant>\n" +
            "    </Recipient>\n" +
            "    <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://www.cise.eu/accesspoint/service/v1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
            +
            "        <SignedInfo>\n" +
            "            <CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n"
            +
            "            <SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>\n"
            +
            "            <Reference URI=\"\">\n" +
            "                <Transforms>\n" +
            "                    <Transform Algorithm=\"http://www.w3.org/TR/1999/REC-xslt-19991116\">\n"
            +
            "                        <xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:s=\"http://www.w3.org/2000/09/xmldsig#\">\n"
            +
            "                            <xsl:strip-space elements=\"*\"/>\n" +
            "                            <xsl:output indent=\"false\" method=\"xml\" omit-xml-declaration=\"yes\"/>\n"
            +
            "                            <xsl:template match=\"*[not(self::s:Signature)]\">\n" +
            "                                <xsl:element name=\"{local-name()}\">\n" +
            "                                    <xsl:apply-templates select=\"*|text()\"/>\n" +
            "                                </xsl:element>\n" +
            "                            </xsl:template>\n" +
            "                            <xsl:template match=\"s:Signature\"/>\n" +
            "                        </xsl:stylesheet>\n" +
            "                    </Transform>\n" +
            "                    <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n"
            +
            "                </Transforms>\n" +
            "                <DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>\n"
            +
            "                <DigestValue>DJ4PaSZO3jtVykPM0W6FpxGvTKE=</DigestValue>\n" +
            "            </Reference>\n" +
            "        </SignedInfo>\n" +
            "        <SignatureValue>bTahN0EeBy2URZZjW3zvVDsqJf9ko5nakEv9DOHCBeouU7NXo+vUdZKrRxQ8On4nrSCm+Zrf7s1V\n"
            +
            "PiQ0E4+CHC0plAU2DA8DzKjVQkUXlap/NgOhJEVicTEujD5n1gBfSH1qZFErKj1w7s8DxZtbswe2\n" +
            "aEuENXXvcdbSimnTfcNZFBbqjjiYRDh4wRafmKSDibE2SlDjxynEYmQ3j0H7x5Zx/zzZ2y6dYUbv\n" +
            "AvYuj7Mf7ywbO8qynAwT3uFs0PGdhLnJIF68pmL46SLAjV3b1DpG6OQQnqJpMeIvZtHrJL7YE6r6\n" +
            "NMWMpnpgm7MkN0ZWJh8QOeCZLaVZMnFDuyMOsw==</SignatureValue>\n" +
            "        <KeyInfo>\n" +
            "            <X509Data>\n" +
            "                <X509SubjectName>C=cx, DC=eucise, O=nodecx, OU=HOSTS, CN=apache.nodecx.eucise.cx</X509SubjectName>\n"
            +
            "                <X509Certificate>MIIEFzCCAv+gAwIBAgIIPEea4OOlZW8wDQYJKoZIhvcNAQELBQAwPTEdMBsGA1UEAwwUc2lnbmlu\n"
            +
            "Zy1jYS5ldWNpc2UuY3gxDzANBgNVBAoMBmV1Y2lzZTELMAkGA1UEBhMCY3gwHhcNMTkwNTMxMTY1\n" +
            "ODI2WhcNMjkwNTMwMTY1MDM0WjBoMSAwHgYDVQQDDBdhcGFjaGUubm9kZWN4LmV1Y2lzZS5jeDEO\n" +
            "MAwGA1UECwwFSE9TVFMxDzANBgNVBAoMBm5vZGVjeDEWMBQGCgmSJomT8ixkARkWBmV1Y2lzZTEL\n" +
            "MAkGA1UEBhMCY3gwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDk4ziuiP/2OEIvOH14\n" +
            "7uDvGFWSFzaDCdf3tPD/KOgQJEOza7DiI9BLfF+WMowmDqWz700TeSuZhr4BigMNy/jSSGZ0/Tc5\n" +
            "aVAkx0AQRCaifwGyJH/wykx7pJLKgBfk5w0w1tdaaOfeAAyysOx8PK2ZA7gPxn6Md5wDvAcOqD9W\n" +
            "rjP3NS4yKaHeD2qoXkRM/Y/0puWfVl8nUANjcmCo+0YSWf5BRYtRjDcRrymQJbRV94QqPqA+QL5/\n" +
            "a4SoZl50yVLgoQOFCOwTLw9W8DYf0G+Yx5EW3BVjeH0LjnN62AL0DGaq5TBtgvyTLHjbPi5H9+Au\n" +
            "PC96xzZC3/4CAW0QN9VhAgMBAAGjge8wgewwDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBTVkohW\n" +
            "SsFlEtVODrJH3Qs1T9ifGTBJBggrBgEFBQcBAQQ9MDswOQYIKwYBBQUHMAGGLWh0dHA6Ly9lamJj\n" +
            "YTo4MDgwL2VqYmNhL3B1YmxpY3dlYi9zdGF0dXMvb2NzcDAiBgNVHREEGzAZghdhcGFjaGUubm9k\n" +
            "ZWN4LmV1Y2lzZS5jeDAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwEwHQYDVR0OBBYEFDdY\n" +
            "j56IKk4Ef+V5XwE6SqNvGXlUMA4GA1UdDwEB/wQEAwIC/DANBgkqhkiG9w0BAQsFAAOCAQEAkjaj\n" +
            "YBi7/FQl8rA8hOw1+MGCoPqAmKnV3mdph/rwPJKYEQ8/zE2dRIC8tU/HpSleTMqk81xNgbcD/q3O\n" +
            "YeGbU3fK2W6BrgyeMZYuaxiZ1rhWkoUVCv4GEDwXX8LQp4J9MJbvGYiQQhehuPqAF9xTHqTH3jnO\n" +
            "bom+iOLL4GNrEP8AM2gF15ktXvduS3UYOhUDPL7HR4J3bsEJYdaxzwZONg6NrQlcmGjVtNkQvck+\n" +
            "KHYZeiJOji3mNv+urD3J/piD/M7e5NaULy31g/V2SS2nu45YJ7BpfqucUg64RupInSlaf5u1I/gs\n" +
            "AyLlDhpigeQvncGryrt8Uk/V5n7f6t0S9w==</X509Certificate>\n" +
            "            </X509Data>\n" +
            "        </KeyInfo>\n" +
            "    </Signature>\n" +
            "    <AckCode>Success</AckCode>\n" +
            "    <AckDetail>Push message received</AckDetail>\n" +
            "</ns4:Acknowledgement>";
    private static final String SYNC_ACK_MSG_SUCCESS =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n"
            +
            "    <CorrelationID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc</CorrelationID>\n" +
            "    <CreationDateTime>2019-08-22T13:31:57.884Z</CreationDateTime>\n" +
            "    <MessageID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc_be72e828-f851-46a4-a44e-bb6372aa54a6</MessageID>\n"
            +
            "    <Priority>High</Priority>\n" +
            "    <RequiresAck>false</RequiresAck>\n" +
            "    <Sender><!--Required--> " +
            "       <ServiceID>legacysystem.test1.Acknowledgement</ServiceID>" +
            "       <ServiceOperation>Acknowledgement</ServiceOperation>" +
            "       <ServiceStatus>Draft</ServiceStatus>" +
            "       <ServiceType>VesselService</ServiceType>" +
            "       <Participant>" +
            "           <Id>legacysystem.capgemini</Id>" +
            "           <EndpointType>SOAP</EndpointType>" +
            "       </Participant>" +
            "    </Sender>" +
            "    <AckCode>Success</AckCode>\n" +
            "    <AckDetail>Message delivered</AckDetail>\n" +
            "</ns4:Acknowledgement>";
    private static final String SYNC_ACK_MSG_SUCCESS_NO_SENDER =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n"
            +
            "    <CorrelationID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc</CorrelationID>\n" +
            "    <CreationDateTime>2019-08-22T13:31:57.884Z</CreationDateTime>\n" +
            "    <MessageID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc_be72e828-f851-46a4-a44e-bb6372aa54a6</MessageID>\n"
            +
            "    <Priority>High</Priority>\n" +
            "    <RequiresAck>false</RequiresAck>\n" +
            "    <AckCode>Success</AckCode>\n" +
            "    <AckDetail>Message delivered</AckDetail>\n" +
            "</ns4:Acknowledgement>";

    public static String getAsyncAckMsgSucceeded() {
        return ASYNC_ACK_MSG_SUCCEEDED;
    }

    public static String getSyncAckMsgSuccessNoSender() {
        return SYNC_ACK_MSG_SUCCESS_NO_SENDER;
    }

    public static Acknowledgement getSyncAckMsgSuccess() {
        XmlMapper xmlMapper = new DefaultXmlMapper();
        return xmlMapper.fromXML(SYNC_ACK_MSG_SUCCESS);
    }


}
