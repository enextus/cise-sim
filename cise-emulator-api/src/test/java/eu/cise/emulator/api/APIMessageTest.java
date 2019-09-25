package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.resources.WebAPIMessageResource;
import eu.cise.io.MessageStorage;
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

public class APIMessageTest {

    public static MessageProcessor messageProcessor;
    public static MessageStorage messageStorage;

    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        messageProcessor = mock (MessageProcessor.class);
        messageStorage = mock (MessageStorage.class);
        jsonMapper = new ObjectMapper();
    }


    @Test
    public void it_calls_MessageStorage_to_obtain_last_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, messageStorage);
        messageAPI.getLastStoredMessage();
        verify(messageStorage).read();
    }

    @Test
    public void it_returns_empty_when_NO_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor , messageStorage);
        when(messageStorage.read()).thenReturn(null);

        MessageApiDto response = messageAPI.getLastStoredMessage();

        assertThat(response).isNull();
    }

    @Test
    public void it_returns_last_stored_message() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor , messageStorage);
        MessageApiDto mockedMessageApiDto = mock(MessageApiDto.class);
        when(messageStorage.read()).thenReturn(mockedMessageApiDto);

        MessageApiDto response = messageAPI.getLastStoredMessage();

        assertThat(response).isEqualTo(mockedMessageApiDto);

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
