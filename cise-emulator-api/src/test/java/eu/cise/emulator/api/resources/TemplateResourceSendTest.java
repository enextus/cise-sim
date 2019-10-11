package eu.cise.emulator.api.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import eu.cise.emulator.api.TemplateAPI;
import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class TemplateResourceSendTest {

    private static MessageAPI messageAPI = mock(MessageAPI.class);
    private static TemplateAPI templateAPI = mock(TemplateAPI.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
        .addResource(new TemplateResource(messageAPI, templateAPI))
        .bootstrapLogging(false)
        .build();
    private static EmuConfig emuConfig = mock(EmuConfig.class);
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
        when(messageAPI.send(any(), any())).thenReturn(new MessageApiDto(200, "", "", ""));
    }

    @After
    public void after() {
        reset(templateAPI);
        reset(messageAPI);
    }

    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        Response response = resources.target("/api/templates/template-id")
            .request()
            .post(Entity.entity(msgParams(), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void it_invokes_the_send_and_pass_the_message_to_the_facade() {
        Response test = resources.target("/api/templates/template-id")
            .request()
            .post(Entity.entity(msgParams(), MediaType.APPLICATION_JSON_TYPE));
        verify(messageAPI).send(any(), any(JsonNode.class));
    }

    private JsonNode msgParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();

        msgTemplateWithParamObject.put("requiresAck", "false");
        msgTemplateWithParamObject.put("messageId", "1234-123411-123411-1234");
        msgTemplateWithParamObject.put("correlationId", "7777-666666-666666-5555");

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }
}
