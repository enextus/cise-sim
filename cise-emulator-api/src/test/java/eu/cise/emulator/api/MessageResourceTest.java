package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MessageResourceTest {

    private static MsgWithParamMapper msgWithParamMapper = mock(MsgWithParamMapper.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new DefaultMessageResource(msgWithParamMapper))
            .bootstrapLogging(false)
            .build();

    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
    }

    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        Response response = resources.target("/api/messages")
                .request()
                .post(Entity.entity(msgTemplateWithParams(), MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void it_invokes_the_send_and_pass_the_message_to_the_facade() {
        resources.target("/api/messages")
                .request()
                .post(Entity.entity(msgTemplateWithParams(), MediaType.APPLICATION_JSON_TYPE));

        verify(msgWithParamMapper).extractSendParams(any(JsonNode.class));
    }

    private JsonNode msgTemplateWithParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();

        ObjectNode params = jsonMapper.createObjectNode();
        params.put("requires-ack", "false");
        params.put("message-id", "1234-123411-123411-1234");
        params.put("correlation-id", "7777-666666-666666-5555");

        msgTemplateWithParamObject.put("message-template", "hash-msg-template");
        msgTemplateWithParamObject.set("params", params);

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }
}
