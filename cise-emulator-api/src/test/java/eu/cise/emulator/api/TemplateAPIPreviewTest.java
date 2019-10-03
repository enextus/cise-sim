package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.representation.TemplateParams;
import eu.cise.io.MessageStorage;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TemplateAPIPreviewTest {

    public static MessageProcessor messageProcessor;
    public static MessageStorage messageStorage;


    @Before
    public void before() {
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
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
        TemplateAPI templateAPI = new TemplateAPI();
        PreviewResponse previewResponse = templateAPI.preview(new TemplateParams("template-id", "message-id", "correlation-id", false));
        assertThat(previewResponse).isInstanceOf(PreviewResponse.OK.class);
    }

}
