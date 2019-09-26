package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import eu.cise.io.MessageStorage;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static eu.eucise.helpers.AckBuilder.newAck;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CiseMessageResourceTest {
    private static MessageAPI messageAPI = mock(MessageAPI.class);

    private static MessageStorage messageStorage;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new CiseMessageResource(messageAPI, messageStorage))
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
        messageStorage = mock(MessageStorage.class);
    }

    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        String message = MessageBuilderUtil.EXAMPLAR_TEMPLATE_MESSAGE_XML;
        Acknowledgement acknowledgement = newAck().build();
        when(messageAPI.receive(message)).thenReturn(acknowledgement);

        CiseMessageResource ciseMessageResource = new CiseMessageResource(messageAPI, messageStorage);
        Response response = ciseMessageResource.receive(message);
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void it_invokes_the_send_and_stores_the_acknowledge() {
        String message = MessageBuilderUtil.EXAMPLAR_TEMPLATE_MESSAGE_XML;
        Acknowledgement acknowledgement = MessageBuilderUtil.createAcknowledgeMessage();
        when(messageAPI.receive(message)).thenReturn(acknowledgement);

        CiseMessageResource ciseMessageResource = new CiseMessageResource(messageAPI, messageStorage);
        ciseMessageResource.receive(message);

        verify(messageStorage).store(any());
    }

}
