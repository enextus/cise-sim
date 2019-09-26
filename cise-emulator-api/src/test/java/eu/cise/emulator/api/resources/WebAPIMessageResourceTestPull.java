package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WebAPIMessageResourceTestPull {

    private static MessageAPI messageAPI = mock(MessageAPI.class);;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new WebAPIMessageResource(messageAPI))
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
    }


    @Test
    public void it_invokes_the_pull_and_makes_a_call_to_MessageAPI_to_get_the_last_stored_message() {
        Response test = resources.target("/webapi/messages")
                .request()
                .get();
        verify(messageAPI).getLastStoredMessage();
    }

    @Test
    public void it_invokes_the_pull_and_obtains_the_last_stored_message_from_MessageAPI_with_success() {
        MessageApiDto storedMessage = new MessageApiDto(Response.Status.OK.getStatusCode(), "error-for-test", "","");
        when(messageAPI.getLastStoredMessage()).thenReturn(storedMessage);
        Response resourceResponse = resources.target("/webapi/messages")
                .request()
                .get();
        MessageApiDto entity = resourceResponse.readEntity(MessageApiDto.class);

        assertThat(entity.getErrorDetail()).isEqualTo(storedMessage.getErrorDetail());
    }

}
