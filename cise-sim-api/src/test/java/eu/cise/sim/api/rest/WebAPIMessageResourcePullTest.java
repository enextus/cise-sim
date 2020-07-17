package eu.cise.sim.api.rest;

import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.messages.UiMessageResource;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class WebAPIMessageResourcePullTest {

    private static MessageAPI messageAPI;

    private UiMessageResource webAPIMessageResource;

    @Before
    public void before() {
        messageAPI = mock(MessageAPI.class);
        webAPIMessageResource = new UiMessageResource(messageAPI);
    }

}
