package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WebAPIMessageResourceTest {

    private static MessageAPI messageAPI = mock(MessageAPI.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new WebAPIMessageResource(messageAPI))
            .bootstrapLogging(false)
            .build();

    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
    }

    @Ignore //TODO: solve the response
    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        Response response = resources.target("/webapi/messages")
                .request()
                .post(Entity.entity(msgTemplateWithParams(), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void it_invokes_the_send_and_pass_the_message_to_the_facade() {
        Response test = resources.target("/webapi/messages")
                .request()
                .post(Entity.entity(msgTemplateWithParams(), MediaType.APPLICATION_JSON_TYPE));
        verify(messageAPI).send(any(JsonNode.class));
    }

    @Test
    public void it_invokes_the_receive_and_makes_a_call_to_MessageAPI_to_get_the_last_stored_message() {
        Response test = resources.target("/webapi/messages")
                .request()
                .get();
        verify(messageAPI).getLastStoredMessage();
    }

    @Test
    public void it_invokes_the_receive_and_obtains_the_last_stored_message_from_MessageAPI_with_success() {
        MessageApiDto storedMessage = new MessageApiDto(Response.Status.OK, "error-for-test", "","");
        when(messageAPI.getLastStoredMessage()).thenReturn(storedMessage);
        Response resourceResponse = resources.target("/webapi/messages")
                .request()
                .get();
        MessageApiDto entity = resourceResponse.readEntity(MessageApiDto.class);

        assertThat(entity.getErrorDetail()).isEqualTo(storedMessage.getErrorDetail());
    }



    private JsonNode msgTemplateWithParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();

        ObjectNode params = jsonMapper.createObjectNode();
        params.put("requires_ack", "false");
        params.put("message_id", "1234-123411-123411-1234");
        params.put("correlation_id", "7777-666666-666666-5555");

        msgTemplateWithParamObject.put("message_template", "hash-msg-template");
        msgTemplateWithParamObject.set("params", params);

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }
}
