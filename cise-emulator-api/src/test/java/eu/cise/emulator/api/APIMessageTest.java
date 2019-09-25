package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.resources.WebAPIMessageResource;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class APIMessageTest {

    public static final MessageProcessor messageProcessor = mock (MessageProcessor.class);


    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
    }


    @Test
    public void it_calls_MessageProcessor_to_obtain_last_stored_message() {

        try {
            MessageAPI test = new DefaultMessageAPI(messageProcessor);
            test.getLastStoredMessage();
        } catch (Exception e) {
            // do nothing
        }
        verify(messageProcessor).getLastStoredMessage();
    }


    @Ignore
    @Test
    public void it_return_empty_when_NO_stored_message() {
        try {
            MessageAPI test = new DefaultMessageAPI(messageProcessor);
            test.getLastStoredMessage();
        } catch (Exception e) {
            // do nothing
        }
    }

    @Ignore
    @Test
    public void it_return_last_stored_message() {

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
