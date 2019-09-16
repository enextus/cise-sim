package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.SendParam;
import org.junit.Before;
import org.junit.Ignore;
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
    public void it_extracts_from_the_json_message_the_messageId_value() {
        SendParam actual = msgWithParamMapper.extractSendParams(msgWithParams());
        assertThat(actual.getMessageId()).isEqualTo("1234-123411-123411-1234");
    }

    @Test
    public void it_extracts_from_the_json_message_the_CorrelationId_value() {
        SendParam actual = msgWithParamMapper.extractSendParams(msgWithParams());
        assertThat(actual.getCorrelationId()).isEqualTo("7777-666666-666666-5555");
    }

    @Test
    public void it_extracts_from_the_json_message_the_RequireAck_value() {
        SendParam actual = msgWithParamMapper.extractSendParams(msgWithParams());
        assertThat(actual.isRequiresAck()).isEqualTo(true);
    }

    @Ignore
    @Test
    public void it_extracts_from_the_json_message_the_MessageContent_value() {
        String actual = msgWithParamMapper.extractMessageTemplateHash(msgWithParams());
        assertThat(actual).isEqualTo("hash-msg-template");
    }


    private JsonNode msgWithParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        ObjectNode params = jsonMapper.createObjectNode();
        params.put("requires-ack", true);
        params.put("message-id", "1234-123411-123411-1234");
        params.put("correlation-id", "7777-666666-666666-5555");

        msgTemplateWithParamObject.put("message-template", "hash-msg-template");
        msgTemplateWithParamObject.set("params", params);

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}
