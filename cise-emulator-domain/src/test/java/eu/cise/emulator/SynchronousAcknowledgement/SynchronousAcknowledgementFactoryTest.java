package eu.cise.emulator.SynchronousAcknowledgement;

import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.eucise.helpers.PushBuilder;
import eu.eucise.helpers.ServiceBuilder;
import eu.eucise.helpers.ServiceProfileBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;

public class SynchronousAcknowledgementFactoryTest {

    private XmlMapper prettyXmlNotValidMapper;
    private PushBuilder messageBuilder;
    private ServiceBuilder serviceBuilder;


    @Before
    public void before() {
        prettyXmlNotValidMapper = new DefaultXmlMapper.PrettyNotValidating();
        serviceBuilder = newService().id("my.fake.id");
        messageBuilder = newPush()
                .sender(serviceBuilder.build())
                .recipient(newService().id("recipient-id"))
                .correlationId("fakeId")
                .id("fakeId")
                .informationSensitivity(InformationSensitivityType.GREEN)
                .informationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED)
                .purpose(PurposeType.NON_SPECIFIED);
    }

    @Test
    public void it_convert_success_to_acknowledge_with_success_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(messageBuilder.build(), SynchronousAcknowledgementType.SUCCESS, "");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }


    @Test
    public void it_convert_error_Xmlmalformed_to_acknowledge_with_BadRequest_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(messageBuilder.build(), SynchronousAcknowledgementType.XML_MALFORMED, "xml ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }

    @Test
    public void it_convert_error_InternalError_to_acknowledge_with_BadRequest_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(messageBuilder.build(), SynchronousAcknowledgementType.INTERNAL_ERROR, "unknow ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }


    @Test
    public void it_convert_error_Semantic_to_acknowledge_with_BadRequest_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(messageBuilder.build(), SynchronousAcknowledgementType.SEMANTIC, "semantic rule ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }

    @Test
    public void it_reply_with_DiscoveredProfiles_in_case_of_PushSubscribe_with_no_Recipient_and_no_DiscoveryProfiles() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Push message = messageBuilder
                .sender(serviceBuilder.operation(ServiceOperationType.SUBSCRIBE).build())
                .build();
        message.setRecipient(null);

        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.SUCCESS, "");
        assertThat(acknowledgement.getDiscoveredServices().size()).isGreaterThan(0);
    }

    @Test
    public void it_reply_with_DiscoveredProfiles_in_case_of_PushSubscribe_with_no_Recipient_and_a_DiscoveryProfiles() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();

        Push message = messageBuilder
                .sender(serviceBuilder.operation(ServiceOperationType.SUBSCRIBE).build())
                .addProfile(ServiceProfileBuilder.newProfile().seaBasin(SeaBasinType.ATLANTIC))
                .build();
        message.setRecipient(null);

        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.SUCCESS, "");

        assertThat(acknowledgement.getDiscoveredServices().size()).isGreaterThan(0);
    }

    @Test
    public void it_reply_with_error_in_case_of_PushSubscribe_with_both_Recipient_and_a_DiscoveryProfiles() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();

        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(
                messageBuilder.sender(serviceBuilder.operation(ServiceOperationType.SUBSCRIBE).build())
                        .addProfile(ServiceProfileBuilder.newProfile().seaBasin(SeaBasinType.ATLANTIC))
                        .build(),
                SynchronousAcknowledgementType.SUCCESS,
                "");

        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }

    @Test
    public void it_reply_with_error_in_case_of_Push_not_Subscribe_without_no_Recipient_and_no_DiscoveryProfiles() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();

        Push message = messageBuilder
                .build();
        message.setRecipient(null);

        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.SUCCESS, "");

        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }


}