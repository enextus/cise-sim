package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.representation.TemplateParams;
import eu.cise.emulator.exceptions.NullSendParamEx;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.DefaultTemplateLoader;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TemplateAPIPreviewTest {

    public static MessageProcessor messageProcessor;
    public static MessageStorage messageStorage;
    private TemplateAPI templateAPI;
    private TemplateParams templateParams;
    private TemplateLoader templateLoader;
    private XmlMapper xmlMapper;


    @Before
    public void before() {
        xmlMapper = mock(XmlMapper.class);
        templateLoader = mock(DefaultTemplateLoader.class);
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
        templateAPI = new TemplateAPI(messageProcessor, templateLoader, xmlMapper);
        templateParams = new TemplateParams("template-id", "message-id", "correlation-id", false);
        when(messageProcessor.preview(any(), any())).thenReturn(mock(Message.class));
        when(templateLoader.loadTemplate(any())).thenReturn(mock(Template.class));
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
    public void it_returns_with_an_error() {
        when(messageProcessor.preview(any(), any())).thenThrow(NullSendParamEx.class);
        PreviewResponse previewResponse = templateAPI.preview(templateParams);
        assertThat(previewResponse).isInstanceOf(PreviewResponse.KO.class);
    }

    @Test
    public void it_calls_the_messageProcecssor_to_prepare_the_message() {
        templateAPI.preview(templateParams);
        verify(messageProcessor).preview(any(), any());
    }

    @Test
    public void it_loads_the_message_to_pass_to_the_message_processor() {
        templateAPI.preview(templateParams);
        verify(templateLoader).loadTemplate(any());
    }

    @Test
    public void it_pass_the_parameters_to_the_messageProcessor() {
        when(xmlMapper.fromXML(any())).thenReturn(mock(Message.class));
        templateAPI.preview(templateParams);
        SendParam sendParams = new SendParam(templateParams.isRequestAck(), templateParams.getMessageId(), templateParams.getCorrelationId());

        verify(messageProcessor).preview(any(), eq(sendParams));
    }
}

