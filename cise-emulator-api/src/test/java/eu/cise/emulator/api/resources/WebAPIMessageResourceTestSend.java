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

public class WebAPIMessageResourceTestSend {

    private static MessageAPI messageAPI = mock(MessageAPI.class);;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new WebAPIMessageResource(messageAPI))
            .bootstrapLogging(false)
            .build();

    private ObjectMapper jsonMapper;

    @Before
    public void before() {
//        messageAPI = mock(MessageAPI.class);
        jsonMapper = new ObjectMapper();
    }

    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        Response response = resources.target("/webapi/messages")
                .request()
                .method("PATCH", Entity.entity(msgTemplateWithParams(), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void it_invokes_the_send_and_pass_the_message_to_the_facade() {
        Response test = resources.target("/webapi/messages")
                .request()
                .method("PATCH", Entity.entity(msgTemplateWithParams(), MediaType.APPLICATION_JSON_TYPE));
        verify(messageAPI).send(any(JsonNode.class));
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
