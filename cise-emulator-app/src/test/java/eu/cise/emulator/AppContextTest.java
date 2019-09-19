package eu.cise.emulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.api.CiseEmulatorAPI;
//import eu.cise.emulator.deprecated.web.app.transport.JerseyRestClient;
import javax.ws.rs.client.*;

import eu.cise.servicemodel.v1.message.Message;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AppContextTest {

    private AppContext appContext;

    private ObjectMapper jsonMapper;


    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
        appContext = new DefaultAppContext();
    }


    @Test
    public void it_builds_message_processor() {
        MessageProcessor messageProcessorConcrete = appContext.makeMessageProcessor();

        assertThat(messageProcessorConcrete).isNotNull();
    }

    @Test
    public void it_builds_web_api() {
        AppContext appContext = new DefaultAppContext();
        MessageProcessor messageProcessor = mock(MessageProcessor.class);

        CiseEmulatorAPI api = appContext.makeEmulatorApi(messageProcessor);

        assertThat(api).isNotNull();
    }

    @Ignore
    @Test
    public void it_connects_API_to_MessageProcesor() {
        EmulatorEngine engine = mock(EmulatorEngine.class);
        MessageProcessor messageProcessor = new DefaultMessageProcessor(engine);

        AppContext appContext = new DefaultAppContext();
        CiseEmulatorAPI resourceApi = appContext.makeEmulatorApi(messageProcessor);

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/api/messages");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        JsonNode json=msgTemplateWithParams();
        Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));


        verify(engine).prepare(any(Message.class), any(SendParam.class));
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