package eu.cise.sim.dropw.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.sim.api.DefaultTemplateAPI;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.MessageResponse;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.dropw.resources.TemplateResource;
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
    private static final DefaultTemplateAPI DEFAULT_TEMPLATE_API = mock(DefaultTemplateAPI.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, DEFAULT_TEMPLATE_API))
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
        when(messageAPI.send(any(), any())).thenReturn(new  ResponseApi<MessageResponse>(new MessageResponse(new Push(), new Acknowledgement())));
    }

    @After
    public void after() {
        reset(DEFAULT_TEMPLATE_API);
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
