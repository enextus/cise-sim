package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.SendParam;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MsgWithParamTest {

    private MsgWithParamMapper msgWithParamMapper;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
        msgWithParamMapper = new MsgWithParamMapper();
    }

    @Test
    public void it_extracts_from_the_json_message_in_a_type_of_SendParams() {
        assertThat(msgWithParamMapper.extractSendParams(msgWithParams())).isInstanceOf(SendParam.class);
    }

    @Test

    public void it_extracts_from_the_json_message_the_SendParams_values() {
        SendParam actual = msgWithParamMapper.extractSendParams(msgWithParams());
        assertThat(actual.getMessageId()).isEqualTo("1234-123411-123411-1234");
    }

    private JsonNode msgWithParams() {
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
