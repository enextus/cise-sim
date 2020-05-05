package eu.cise.sim.api.rest;

import eu.cise.sim.api.MessageAPI;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WebAPIMessageResourcePullTest {

    private static MessageAPI messageAPI;

    private UiMessageResource webAPIMessageResource;

    @Before
    public void before() {
        messageAPI = mock(MessageAPI.class);
        webAPIMessageResource = new UiMessageResource(messageAPI);
    }


    @Test
    public void it_invokes_the_pull_and_makes_a_call_to_MessageAPI_to_get_the_last_stored_message() {
        Response test = webAPIMessageResource.pullAndDelete();
        verify(messageAPI).getLastStoredMessage();
    }

}
