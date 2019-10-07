package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WebAPIMessageResourcePullTest {

    private static MessageAPI messageAPI;

    private WebAPIMessageResource webAPIMessageResource;

    @Before
    public void before() {
        messageAPI = mock(MessageAPI.class);
        webAPIMessageResource = new WebAPIMessageResource(messageAPI);
    }


    @Test
    public void it_invokes_the_pull_and_makes_a_call_to_MessageAPI_to_get_the_last_stored_message() {
        Response test = webAPIMessageResource.pull();
        verify(messageAPI).getLastStoredMessage();
    }

    @Test
    public void it_invokes_the_pull_and_obtains_the_last_stored_message_from_MessageAPI_with_success() {
        MessageApiDto expectedMessage = new MessageApiDto(Response.Status.OK.getStatusCode(), "error-for-test", "", "");

        when(messageAPI.getLastStoredMessage()).thenReturn(expectedMessage);

        Response resourceResponse = webAPIMessageResource.pull();
        MessageApiDto actualMessage = (MessageApiDto) resourceResponse.getEntity();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}
