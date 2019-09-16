package eu.cise.emulator;

import eu.cise.emulator.utils.FakeSignatureService;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MessageProcessorTest {

    private SignatureService signatureService;
    private EmuConfig config;
    private MessageProcessor messageProcessor;

    @Before
    public void before() {
        signatureService = new FakeSignatureService();
        config = mock(EmuConfig.class);
        messageProcessor = new DefaultMessageProcessor(signatureService, config);
    }

    @Test
    public void it_creates_a_preview_message () {
        Message message= newPush().sender(newService().type(VESSEL_SERVICE)).build();
        SendParam param = new SendParam(true, "messageId", "correlationId");

        Message previewMessage = messageProcessor.preview(message, param);

        assertThat(previewMessage).isNotNull();
    }

    @Test
    public void it_calls_prepare_and_send_from_emulator_engine () {
        Message message= newPush().sender(newService().type(VESSEL_SERVICE)).build();
        SendParam param = new SendParam(true, "messageId", "correlationId");

        Acknowledgement acknowledgement = messageProcessor.send(message, param);

        DefaultEmulatorEngine engine = mock(DefaultEmulatorEngine.class);
        verify(engine).prepare(message, param);
        verify(engine).send(message, param);
    }


}
