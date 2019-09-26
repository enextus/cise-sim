package eu.cise.emulator;


import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.exceptions.CreationDateErrorEx;
import eu.cise.emulator.exceptions.EndpointErrorEx;
import eu.cise.emulator.exceptions.EndpointNotFoundEx;
import eu.cise.emulator.exceptions.NullSenderEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;

import static eu.cise.servicemodel.v1.message.AcknowledgementType.SUCCESS;
import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmulatorEngineTest {

    private static final String ENDPOINT_URL = "endpointUrl";

    private static final String ASYNC_ACKNOWLEDGEMENT_MSG_SECCEDEED = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
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
            "            <EndpointUrl>http://192.168.42.37:8380/sim-LSA/CISEMessageService</EndpointUrl>\n" +
            "            <EndpointType>SOAP</EndpointType>\n" +
            "            <ProvidedServicesIds>cx.simlsa2-nodecx.vessel.push.consumer</ProvidedServicesIds>\n" +
            "            <ProvidedServicesIds>cx.simlsa2-nodecx.persondocument.subscribe.consumer</ProvidedServicesIds>\n" +
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
            "            <EndpointUrl>http://192.168.42.37:8280/sim-LSA/CISEMessageService</EndpointUrl>\n" +
            "            <EndpointType>SOAP</EndpointType>\n" +
            "            <ProvidedServicesIds>cx.simlsa1-nodecx.vessel.push.provider</ProvidedServicesIds>\n" +
            "            <ProvidedServicesIds>cx.simlsa1-nodecx.vessel.pull.consumer</ProvidedServicesIds>\n" +
            "            <ProvidedServicesIds>cx.simlsa1-nodecx.persondocument.subscribe.provider</ProvidedServicesIds>\n" +
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
            "    <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns5=\"http://www.cise.eu/accesspoint/service/v1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
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
            "                <DigestValue>DJ4PaSZO3jtVykPM0W6FpxGvTKE=</DigestValue>\n" +
            "            </Reference>\n" +
            "        </SignedInfo>\n" +
            "        <SignatureValue>bTahN0EeBy2URZZjW3zvVDsqJf9ko5nakEv9DOHCBeouU7NXo+vUdZKrRxQ8On4nrSCm+Zrf7s1V\n" +
            "PiQ0E4+CHC0plAU2DA8DzKjVQkUXlap/NgOhJEVicTEujD5n1gBfSH1qZFErKj1w7s8DxZtbswe2\n" +
            "aEuENXXvcdbSimnTfcNZFBbqjjiYRDh4wRafmKSDibE2SlDjxynEYmQ3j0H7x5Zx/zzZ2y6dYUbv\n" +
            "AvYuj7Mf7ywbO8qynAwT3uFs0PGdhLnJIF68pmL46SLAjV3b1DpG6OQQnqJpMeIvZtHrJL7YE6r6\n" +
            "NMWMpnpgm7MkN0ZWJh8QOeCZLaVZMnFDuyMOsw==</SignatureValue>\n" +
            "        <KeyInfo>\n" +
            "            <X509Data>\n" +
            "                <X509SubjectName>C=cx, DC=eucise, O=nodecx, OU=HOSTS, CN=apache.nodecx.eucise.cx</X509SubjectName>\n" +
            "                <X509Certificate>MIIEFzCCAv+gAwIBAgIIPEea4OOlZW8wDQYJKoZIhvcNAQELBQAwPTEdMBsGA1UEAwwUc2lnbmlu\n" +
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

    private static final String SYNCH_ACKNOWLEDGEMENT_MSG_SUCCESS = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
            "    <CorrelationID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc</CorrelationID>\n" +
            "    <CreationDateTime>2019-08-22T13:31:57.884Z</CreationDateTime>\n" +
            "    <MessageID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc_be72e828-f851-46a4-a44e-bb6372aa54a6</MessageID>\n" +
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

    private static final String SYNCH_ACKNOWLEDGEMENT_MSG_SUCCESS_NO_SENDER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
            "    <CorrelationID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc</CorrelationID>\n" +
            "    <CreationDateTime>2019-08-22T13:31:57.884Z</CreationDateTime>\n" +
            "    <MessageID>addb96b1-00b2-4a0a-928e-446bfd7a0ffc_be72e828-f851-46a4-a44e-bb6372aa54a6</MessageID>\n" +
            "    <Priority>High</Priority>\n" +
            "    <RequiresAck>false</RequiresAck>\n" +
            "    <AckCode>Success</AckCode>\n" +
            "    <AckDetail>Message delivered</AckDetail>\n" +
            "</ns4:Acknowledgement>";


    private SignatureService signatureService;
    private EmuConfig config;
    private MessageProcessor messageProcessor;
    private EmulatorEngine engine;
    private Dispatcher dispatcher;
    private Push message;

    @Before
    public void before() {
        signatureService = mock(SignatureService.class);//new FakeSignatureService();
        config = mock(EmuConfig.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultEmulatorEngine(signatureService, dispatcher, config);
        message = newPush().build();

        // set the message creation datetime to a valid value
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(Date.from(java.time.ZonedDateTime.now(ZoneId.of("UTC")).toInstant()));
        message.setCreationDateTime(new XMLGregorianCalendarImpl(cal));

        // assign a service
        Service service = new Service();
        service.setServiceID("id");
        service.setServiceType(ServiceType.VESSEL_SERVICE);
        message.setSender(service);

        when(config.serviceId()).thenReturn("service-id");
        when(config.serviceType()).thenReturn(ServiceType.VESSEL_SERVICE);
        when(config.endpointUrl()).thenReturn(ENDPOINT_URL);
    }

    @After
    public void after() {
        reset(config);
    }

    @Test
    public void it_sends_message_successfully() {
        DispatchResult dispatchResult = new DispatchResult(true, SYNCH_ACKNOWLEDGEMENT_MSG_SUCCESS);
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(dispatchResult);

        engine.send(message);

        verify(dispatcher).send(message, ENDPOINT_URL);
    }

    @Test
    public void it_sends_a_message_failing_the_dispatch_for_end_point_not_found() {
        when(dispatcher.send(message, ENDPOINT_URL)).thenThrow(DispatcherException.class);

        assertThatExceptionOfType(EndpointNotFoundEx.class)
                .isThrownBy(() -> engine.send(message))
                .withMessageContaining("endpoint not found");
    }

    @Test
    public void it_sends_a_message_getting_a_successful_response_and_returns_the_acknowledge() {
        DispatchResult dispatchResult = new DispatchResult(true, SYNCH_ACKNOWLEDGEMENT_MSG_SUCCESS);
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(dispatchResult);

        Acknowledgement ack = null;
        ack = engine.send(message);

        assertThat(ack.getAckCode()).isEqualTo(SUCCESS);
    }

    @Test
    public void it_sends_a_message_getting_an_unsuccessful_response() {
        DispatchResult dispatchResult = new DispatchResult(false, "");
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(dispatchResult);

        assertThatExceptionOfType(EndpointErrorEx.class)
                .isThrownBy(() -> engine.send(message))
                .withMessageContaining("endpoint returned an error");
    }


    @Test
    public void it_adds_a_sender_upon_a_successful_response_without_the_sender_tag() {
        DispatchResult dispatchResult = new DispatchResult(true, SYNCH_ACKNOWLEDGEMENT_MSG_SUCCESS_NO_SENDER);
        when(dispatcher.send(message, config.endpointUrl())).thenReturn(dispatchResult);

        Acknowledgement ack = null;
        ack = engine.send(message);

        assertThat(ack.getAckCode()).isEqualTo(SUCCESS);
    }

    @Test
    public void it_receives_a_valid_message() {
        try {
            engine.receive(message);
        } catch (Exception e) {
            fail("Receive raised an exception");
        }
    }

    @Test
    public void it_receives_a_message_with_creation_datetime_equals_to_current_time_minus_3_hours() {
        Message message = newPush().build();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(Date.from(java.time.ZonedDateTime.now(ZoneId.of("UTC")).toInstant().minus(3, ChronoUnit.HOURS)));

        message.setCreationDateTime(new XMLGregorianCalendarImpl(cal));

        assertThatExceptionOfType(CreationDateErrorEx.class)
                .isThrownBy(() -> engine.receive(message))
                .withMessageContaining("outside the allowed range");
    }

    @Ignore
    @Test
    public void it_receives_a_message_with_creation_datetime_after_5_minutes_of_current_time() {
        Message message = newPush().build();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(Date.from(java.time.ZonedDateTime.now(ZoneId.of("UTC")).toInstant().plus(5, ChronoUnit.MINUTES)));

        message.setCreationDateTime(new XMLGregorianCalendarImpl(cal));

        assertThatExceptionOfType(CreationDateErrorEx.class)
                .isThrownBy(() -> engine.receive(message))
                .withMessageContaining("outside the allowed range");
    }

    @Test
    public void it_receives_a_valid_message_with_wrong_service_type() {
        when(config.serviceType()).thenReturn(ServiceType.EVENT_DOCUMENT_SERVICE);

        Acknowledgement ack = null;
        ack = engine.receive(message);

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SERVICE_TYPE_NOT_SUPPORTED);
        assertThat(ack.getAckDetail()).isEqualTo("Supported service type is VesselService");
    }

    @Test
    public void it_receives_a_valid_message_without_sender() {
        // prepare a message without sender service
        Push message = newPush().build();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(Date.from(java.time.ZonedDateTime.now(ZoneId.of("UTC")).toInstant()));
        message.setCreationDateTime(new XMLGregorianCalendarImpl(cal));

        assertThatExceptionOfType(NullSenderEx.class)
                .isThrownBy(() -> engine.receive(message))
                .withMessageContaining("The sender of the message passed can't be null.");
    }

    @Test
    public void it_receives_a_valid_message_and_returns_the_acknowledge() {
        Acknowledgement ack = null;
        ack = engine.receive(message);

        assertThat(ack.getAckCode()).isEqualTo(SUCCESS);
    }
}