package eu.cise.sim;

import eu.cise.sim.engine.*;
import eu.cise.sim.utils.FakeSignatureService;
import eu.cise.sim.utils.Pair;
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
    private SimConfig config;
    private MessageProcessor messageProcessor;
    private SimEngine engine;
    private SendParam param;

    @Before
    public void before() {
        signatureService = new FakeSignatureService();
        config = mock(SimConfig.class);
        engine = mock(SimEngine.class);
        messageProcessor = new DefaultMessageProcessor(engine);
        param = new SendParam(true, "messageId", "correlationId");
    }

    @Test
    public void it_creates_a_preview_message() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();

        when(engine.prepare(message, param)).thenReturn(message);

        Message previewMessage = messageProcessor.preview(message, param);

        assertThat(previewMessage).isNotNull();
    }

    @Test
    public void it_calls_prepare_from_emulator_engine() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();

        messageProcessor.send(message, param);

        verify(engine).prepare(message, param);
    }

    @Test
    public void it_calls_send_from_emulator_engine() {
        Message message = newPush().sender(newService().type(VESSEL_SERVICE)).build();
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
        Acknowledgement acknowledgement = newAck().build();

        when(engine.prepare(message, param)).thenReturn(preparedMessage);
        when(engine.send(preparedMessage)).thenReturn(acknowledgement);

        Pair<Acknowledgement, Message> sendResponse = messageProcessor.send(message, param);

        assertThat(sendResponse.getA()).isNotNull();
        assertThat(sendResponse.getB()).isEqualTo(preparedMessage);
    }

}
