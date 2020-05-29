package eu.cise.sim.api.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.SendResponse;
import eu.cise.sim.api.TemplateAPI;
import eu.cise.sim.api.dto.MessageApiDto;
import eu.cise.sim.templates.Template;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TemplateResourceSendTest {

    private static final MessageAPI messageAPI = mock(MessageAPI.class);
    private static final TemplateAPI templateAPI = mock(TemplateAPI.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, templateAPI))
            .bootstrapLogging(false)
            .build();

    private XmlMapper xmlMapper;
    private Template expectedTemplate;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        xmlMapper = new DefaultXmlMapper();
        jsonMapper = new ObjectMapper();
        expectedTemplate = new Template("template-id-#1", "name-#1");
        when(messageAPI.send(any(), any())).thenReturn(new SendResponse.OK(new MessageApiDto( "", "")));
    }

    @After
    public void after() {
        reset(templateAPI);
        reset(messageAPI);
    }

    @Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        Response response = resources.target("/ui/templates/template-id")
                .request()
                .post(Entity.entity(msgParams(), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void it_invokes_the_send_and_pass_the_message_to_the_facade() {
        Response test = resources.target("/ui/templates/template-id")
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
