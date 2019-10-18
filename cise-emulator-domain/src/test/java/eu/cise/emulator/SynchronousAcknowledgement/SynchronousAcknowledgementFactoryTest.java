package eu.cise.emulator.SynchronousAcknowledgement;

import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.Service;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SynchronousAcknowledgementFactoryTest {

    private XmlMapper prettyXmlNotValidMapper;
    private Message message;


    @Before
    public void before() {
        prettyXmlNotValidMapper = new DefaultXmlMapper.PrettyNotValidating();
        Service innerService = new Service();
        innerService.setServiceID("my.fake.id");
        message = new Push();
        message.setSender(innerService);
        message.setCorrelationID("fakeId");
        message.setMessageID("fakeId");
        XmlEntityPayload payload = new XmlEntityPayload();
        payload.setInformationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED);
        payload.setInformationSensitivity(InformationSensitivityType.GREEN);
        payload.setPurpose(PurposeType.NON_SPECIFIED);
        message.setPayload(payload);


    }

    @Test
    public void it_convert_success_to_acknowledge_with_success_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.SUCCESS, "");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }


    @Test
    public void it_convert_error_Xmlmalformed_to_acknowledge_with_BadRequest_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.XML_MALFORMED, "xml ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }

    @Test
    public void it_convert_error_InternalError_to_acknowledge_with_BadRequest_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.INTERNAL_ERROR, "unknow ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }


    @Test
    public void it_convert_error_Semantic_to_acknowledge_with_BadRequest_Code() {
        SynchronousAcknowledgementFactory asyncSynchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();
        Acknowledgement acknowledgement = asyncSynchronousAcknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.SEMANTIC, "semantic rule ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
    }
}