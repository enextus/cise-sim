package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import eu.cise.emulator.api.TemplateAPI;
import eu.cise.emulator.api.representation.SendingDataWrapper;
import eu.cise.emulator.api.representation.TemplateParams;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetTemplateTest {


    private static TemplateAPI templateAPI = mock(TemplateAPI.class);
    private static MessageAPI MessageAPI = mock(MessageAPI.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(MessageAPI, templateAPI))
            .bootstrapLogging(false)
            .build();
    private WebAPIMessageResource webAPIMessageResource;
    private SendingDataWrapper dataWrapper;
    private SendParam sendParam;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        sendParam = new SendParam(true, "message-id", null);
        jsonMapper = new ObjectMapper();
        webAPIMessageResource = new WebAPIMessageResource(mock(MessageAPI.class));
        dataWrapper = new SendingDataWrapper(sendParam, "template-hash");
    }

    /*
        private String templateHash;
        private boolean requiresAck;
        private String messageId;
        private String correlationId;
    */
    @Test
    public void it_checks_that_the_api_templates_route_exists() {
        Response response = resources.target("/api/templates/1234567")
                .queryParam("requiresAck", false)
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .request().get();

        assertThat(response.getStatus()).isEqualTo(200);
    }
//MessageApiDto preview(SendParam jsonNode, String templateHash);

    // MessageApiDto preview(TemplateParams params)
    @Test
    public void it_invokes_the_api_templates_for_preview() {
        resources.target("/api/templates/1234567")
                .queryParam("requiresAck", false)
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .request().get();

        verify(templateAPI).preview(any(TemplateParams.class));
    }

    @Test
    @Ignore
    public void it_invokes_the_preview_and_pass_the_sendParam_to_the_facade() {
        webAPIMessageResource.preview(dataWrapper);
        verify(templateAPI).preview(any());
    }

    @Test
    @Ignore
    public void it_invokes_the_preview_and_returns_the_prepared_message() {
        MessageApiDto dtoToReturn = mock(MessageApiDto.class);
        when(mock(MessageAPI.class).preview(any(), any())).thenReturn(dtoToReturn);

        Response response = webAPIMessageResource.preview(dataWrapper);

        MessageApiDto responseEntity = (MessageApiDto) response.getEntity();

        assertThat(responseEntity).isNotNull();
    }

}
