package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.helpers.SendParamsReader;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class paramReaderTest {

    private SendParamsReader paramReader;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
        paramReader = new SendParamsReader();
    }

    @Test
    public void it_extracts_from_the_json_message_the_messageId_value() {
        SendParam actual = paramReader.extractParams(msgWithParams());
        assertThat(actual.getMessageId()).isEqualTo("1234-123411-123411-1234");
    }

    @Test
    public void it_extracts_from_the_json_message_the_CorrelationId_value() {
        SendParam actual = paramReader.extractParams(msgWithParams());
        assertThat(actual.getCorrelationId()).isEqualTo("7777-666666-666666-5555");
    }

    @Test
    public void it_extracts_from_the_json_message_the_RequireAck_value() {
        SendParam actual = paramReader.extractParams(msgWithParams());
        assertThat(actual.isRequiresAck()).isEqualTo(true);
    }




    private JsonNode msgWithParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        ObjectNode params = jsonMapper.createObjectNode();
        params.put("requires_ack", true);
        params.put("message_id", "1234-123411-123411-1234");
        params.put("correlation_id", "7777-666666-666666-5555");

        msgTemplateWithParamObject.put("message_template", "hash-msg-template");
        msgTemplateWithParamObject.set("params", params);

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}
