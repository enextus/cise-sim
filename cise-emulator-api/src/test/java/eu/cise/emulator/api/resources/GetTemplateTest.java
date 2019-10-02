package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import eu.cise.emulator.api.representation.SendingDataWrapper;
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


    private static MessageAPI messageAPI = mock(MessageAPI.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI))
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
        webAPIMessageResource = new WebAPIMessageResource(messageAPI);
        dataWrapper = new SendingDataWrapper(sendParam, "template-hash");
    }

    /*
        private String templateHash;
        private boolean requiresAck;
        private String messageId;
        private String correlationId;
    */
    @Test
    public void it_invokes_the_api_templates_route_exists() {
        Response response = resources.target("/api/templates/1234567")
                .queryParam("requiresAck", false)
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .request().get();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    @Ignore

    public void it_invokes_the_preview_the_http_is_successful_200() {
        Response response = webAPIMessageResource.preview(dataWrapper);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void it_invokes_the_preview_and_pass_the_sendParam_to_the_facade() {
        webAPIMessageResource.preview(dataWrapper);
        verify(messageAPI).preview(any(), any());
    }

    @Test
    @Ignore
    public void it_invokes_the_preview_and_returns_the_prepared_message() {
        MessageApiDto dtoToReturn = mock(MessageApiDto.class);
        when(messageAPI.preview(any(), any())).thenReturn(dtoToReturn);

        Response response = webAPIMessageResource.preview(dataWrapper);

        MessageApiDto responseEntity = (MessageApiDto) response.getEntity();

        assertThat(responseEntity).isNotNull();
    }

}
