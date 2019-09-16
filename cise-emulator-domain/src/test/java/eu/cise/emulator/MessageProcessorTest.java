package eu.cise.emulator;

import eu.cise.emulator.utils.FakeSignatureService;
import eu.cise.servicemodel.v1.message.Message;
import org.junit.Before;
import org.junit.Test;

import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MessageProcessorTest {

    private SignatureService signatureService;
    private EmuConfig config;

    @Before
    public void before() {
        signatureService = new FakeSignatureService();
        config = mock(EmuConfig.class);
    }

    @Test
    public void it_creates_a_preview_message () {
        MessageProcessor messageProcessor = new DefaultMessageProcessor(signatureService, config);

        Message message= newPush().sender(newService().type(VESSEL_SERVICE)).build();
        SendParam param = new SendParam(true, "messageId", "correlationId");
        Message previewMessage = messageProcessor.preview(message, param);

        assertThat(previewMessage).isNotNull();
    }
}
