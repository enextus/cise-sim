package eu.cise.sim.api;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.exceptions.NullSendParamEx;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.templates.DefaultTemplateLoader;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DefaultTemplateAPIPreviewTest {

    public static MessageProcessor messageProcessor;
    public static MessageStorage messageStorage;
    private TemplateAPI defaultTemplateAPI;
    private TemplateParams templateParams;
    private TemplateLoader templateLoader;
    private XmlMapper xmlMapper;


    @Before
    public void before() {
        xmlMapper = mock(XmlMapper.class);
        templateLoader = mock(DefaultTemplateLoader.class);
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
        defaultTemplateAPI = new DefaultTemplateAPI(messageProcessor, templateLoader, xmlMapper, xmlMapper);
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
        ResponseApi<Template> previewResponse = defaultTemplateAPI.preview(templateParams);
        assertThat(previewResponse.isOk()).isTrue();
    }

    @Test
    public void it_returns_with_an_error() {
        when(messageProcessor.preview(any(), any())).thenThrow(NullSendParamEx.class);
        ResponseApi<Template> previewResponse = defaultTemplateAPI.preview(templateParams);
        assertThat(previewResponse.isOk()).isFalse();
    }

    @Test
    public void it_calls_the_messageProcecssor_to_prepare_the_message() {
        defaultTemplateAPI.preview(templateParams);
        verify(messageProcessor).preview(any(), any());
    }

    @Test
    public void it_loads_the_message_to_pass_to_the_message_processor() {
        defaultTemplateAPI.preview(templateParams);
        verify(templateLoader).loadTemplate(any());
    }

    @Test
    public void it_pass_the_parameters_to_the_messageProcessor() {
        when(xmlMapper.fromXML(any())).thenReturn(mock(Message.class));
        defaultTemplateAPI.preview(templateParams);
        SendParam sendParams = new SendParam(templateParams.isRequestAck(), templateParams.getMessageId(), templateParams.getCorrelationId());

        verify(messageProcessor).preview(any(), eq(sendParams));
    }
}

