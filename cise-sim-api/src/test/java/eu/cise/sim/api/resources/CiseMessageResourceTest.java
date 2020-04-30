package eu.cise.sim.api.resources;

import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.io.MessageStorage;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static eu.eucise.helpers.AckBuilder.newAck;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CiseMessageResourceTest {
    private static final MessageAPI messageAPI = mock(MessageAPI.class);

    private static MessageStorage messageStorage;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MessageResource(messageAPI, messageStorage))
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
        messageStorage = mock(MessageStorage.class);
    }

    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        String message = MessageBuilderUtil.TEST_MESSAGE_XML;
        Acknowledgement acknowledgement = newAck().build();
        when(messageAPI.receive(message)).thenReturn(acknowledgement);

        MessageResource ciseMessageResource = new MessageResource(messageAPI, messageStorage);
        Response response = ciseMessageResource.receive(message);
        assertThat(response.getStatus()).isEqualTo(201);
    }


}
