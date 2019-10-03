package eu.cise.emulator.api.resources;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.APIError;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.PreviewResponse;
import eu.cise.emulator.api.TemplateAPI;
import eu.cise.emulator.api.representation.Template;
import eu.cise.emulator.api.representation.TemplateParams;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetTemplateTest {

    private static TemplateAPI templateAPI = mock(TemplateAPI.class);
    private static MessageAPI messageAPI = mock(MessageAPI.class);
    private static EmuConfig emuConfig = mock(EmuConfig.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, templateAPI, emuConfig))
            .bootstrapLogging(false)
            .build();
    private Template expectedTemplate;

    @Before
    public void before() {
        expectedTemplate = new Template("template-id-#1");
        when(templateAPI.preview(any())).thenReturn(new PreviewResponse.OK(expectedTemplate));
    }

    @After
    public void after() {
        reset(templateAPI);
        reset(messageAPI);
    }

    @Test
    public void it_checks_that_the_api_templates_route_exists() {
        Response response = resources.target("/api/templates/1234567")
                .queryParam("requiresAck", false)
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .request().get();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void it_invokes_the_api_templates_for_preview() {
        resources.target("/api/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        verify(templateAPI).preview(any(TemplateParams.class));
    }

    @Test
    public void it_invokes_the_api_templates_for_preview_with_a_valued_templateParams() {
        resources.target("/api/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        verify(templateAPI).preview(aTemplateParams());
    }

    @Test
    public void it_returns_a_template_when_previewResponse_is_ok() {
        Response response = resources.target("/api/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        Template actualTemplate = response.readEntity(Template.class);

        assertThat(actualTemplate).isEqualTo(expectedTemplate);
    }

    @Test
    public void it_returns_a_apiError_when_previewResponse_is_ko() {
        when(templateAPI.preview(any())).thenReturn(new PreviewResponse.KO("exception"));

        Response response = resources.target("/api/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        APIError actualApiError = response.readEntity(APIError.class);

        APIError expectedApiError = new APIError("exception");
        assertThat(actualApiError).isEqualTo(expectedApiError);
    }

    private Template aTemplate() {
        return new Template();
    }

    private TemplateParams aTemplateParams() {
        return new TemplateParams("1234567", "message-id-#1", "correlation-id-#1", false);
    }

}
