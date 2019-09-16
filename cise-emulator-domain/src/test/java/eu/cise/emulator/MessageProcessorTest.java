package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Message;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageProcessorTest {

    @Test
    public void it_creates_a_preview_message () {
        MessageProcessor messageProcessor = new DefaultMessageProcessor();

        Message message= newPush().build();
        SendParam param = new SendParam(true, "messageId", "correlationId");

        Message previewMessage = messageProcessor.preview(message, param);

        assertThat(previewMessage).isNotNull();
    }
}
