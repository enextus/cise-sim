package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.api.helpers.SendParamsReader;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParamReaderTest {

    private SendParamsReader paramReader;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
        paramReader = new SendParamsReader();
    }

    @Test
    public void it_extracts_from_the_json_message_the_messageId_value() {
        SendParam actual = paramReader.extractParams(msgParams());
        assertThat(actual.getMessageId()).isEqualTo("1234-123411-123411-1234");
    }

    @Test
    public void it_extracts_from_the_json_message_the_CorrelationId_value() {
        SendParam actual = paramReader.extractParams(msgParams());
        assertThat(actual.getCorrelationId()).isEqualTo("7777-666666-666666-5555");
    }

    @Test
    public void it_extracts_from_the_json_message_the_RequireAck_value() {
        SendParam actual = paramReader.extractParams(msgParams());
        assertThat(actual.isRequiresAck()).isEqualTo(true);
    }




    private JsonNode msgParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        msgTemplateWithParamObject.put("requiresAck", true);
        msgTemplateWithParamObject.put("messageId", "1234-123411-123411-1234");
        msgTemplateWithParamObject.put("correlationId", "7777-666666-666666-5555");

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}
