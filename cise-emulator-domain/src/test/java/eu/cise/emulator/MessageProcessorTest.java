package eu.cise.emulator;

import eu.cise.emulator.utils.FakeSignatureService;
import eu.cise.emulator.utils.Pair;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.signature.SignatureService;
import org.junit.Before;
import org.junit.Test;

import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageProcessorTest {

    private SignatureService signatureService;
    private EmuConfig config;
    private MessageProcessor messageProcessor;
    private EmulatorEngine engine;

    @Before
    public void before() {
        signatureService = new FakeSignatureService();
        config = mock(EmuConfig.class);
        engine = mock(EmulatorEngine.class);
        messageProcessor = new DefaultMessageProcessor(engine);
    }

    @Test
    public void it_creates_a_preview_message() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        SendParam param = new SendParam(true, "messageId", "correlationId");

        when(engine.prepare(message, param)).thenReturn(message);

        Message previewMessage = messageProcessor.preview(message, param);

        assertThat(previewMessage).isNotNull();
    }

    @Test
    public void it_calls_prepare_from_emulator_engine() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        SendParam param = new SendParam(true, "messageId", "correlationId");

        messageProcessor.send(message, param);

        verify(engine).prepare(message, param);
    }

    @Test
    public void it_calls_send_from_emulator_engine() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        SendParam param = new SendParam(true, "messageId", "correlationId");
        Message preparedMessage = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        preparedMessage.setCorrelationID("new_correlation_id");

        when(engine.prepare(message, param)).thenReturn(preparedMessage);

        messageProcessor.send(message, param);

        verify(engine).send(preparedMessage);
    }

    @Test
    public void it_calls_send_with_success_and_returns_a_pair_containing_the_prepared_message_with_the_acknowledge() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();
        Push preparedMessage = mock(Push.class);
        SendParam param = new SendParam(true, "messageId", "correlationId");
        Acknowledgement acknowledgement = newAck().build();

        when(engine.prepare(message, param)).thenReturn(preparedMessage);
        when(engine.send(preparedMessage)).thenReturn(acknowledgement);

        Pair<Acknowledgement, Message> sendResposnse = messageProcessor.send(message, param);

        assertThat(sendResposnse.getA()).isNotNull();
        assertThat(sendResposnse.getB()).isEqualTo(preparedMessage);
    }

}
