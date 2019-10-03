package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.representation.TemplateParams;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.servicemodel.v1.message.Message;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TemplateAPIPreviewTest {

    public static MessageProcessor messageProcessor;
    public static MessageStorage messageStorage;
    private TemplateAPI templateAPI;
    private TemplateParams templateParams;


    @Before
    public void before() {
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
        templateAPI = new TemplateAPI(messageProcessor);
        templateParams = new TemplateParams("template-id", "message-id", "correlation-id", false);
    }


    /*
     public PreviewResponse preview(TemplateParams templateParams) {
        try {
            Template template = loadTemplate(templateParams.getTemplateId())
            Message message = messaageProcessor(template.getMessage(), templateParams.getSendParams());
            return new OkPreviewResponse(new Template(template.templateId, temnplate.name, message));

        } catch(Exception e) {
            return new KoPreviewResponse(e.getMessage(), templateParams);
        }
    }
    * */

    @Test
    public void it_returns_a_previewResponse_successfully() {
        PreviewResponse previewResponse = templateAPI.preview(templateParams);
        assertThat(previewResponse).isInstanceOf(PreviewResponse.OK.class);
    }

    @Test
    public void it_calls_the_messageProcecssor_to_prepare_the_message() {
        when(messageProcessor.preview(any(), any())).thenReturn(mock(Message.class));
        templateAPI.preview(new TemplateParams("template-id", "message-id", "correlation-id", false));
        verify(messageProcessor).preview(any(), any());
    }

}
