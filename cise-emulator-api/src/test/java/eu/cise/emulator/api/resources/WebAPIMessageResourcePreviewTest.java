package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import eu.cise.emulator.api.representation.SendingDataWrapper;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WebAPIMessageResourcePreviewTest {

    private static MessageAPI messageAPI;
    private WebAPIMessageResource webAPIMessageResource;
    private SendingDataWrapper dataWrapper;
    private SendParam sendParam;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        messageAPI = mock(MessageAPI.class);
        sendParam = new SendParam(true, "message-id", null);
        jsonMapper = new ObjectMapper();
        webAPIMessageResource = new WebAPIMessageResource(messageAPI);
        dataWrapper = new SendingDataWrapper(sendParam, "template-hash");
    }

    @Test
    public void it_invokes_the_preview_the_http_is_successful_200() {
        Response response = webAPIMessageResource.preview(dataWrapper);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void it_invokes_the_preview_and_pass_the_sendParam_to_the_facade() {
        webAPIMessageResource.preview(dataWrapper);
        verify(messageAPI).preview(any(), any());
    }

    @Test
    public void it_invokes_the_preview_and_returns_the_prepared_message() {
        Response response = webAPIMessageResource.preview(dataWrapper);

        MessageApiDto responseEntity = (MessageApiDto)response.getEntity();

        assertThat(responseEntity).isNotNull();
    }

}
