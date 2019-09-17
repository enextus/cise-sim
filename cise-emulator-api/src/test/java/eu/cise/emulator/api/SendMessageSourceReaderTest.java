package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SendMessageSourceReaderTest {
    private SendMessageSourceReader sourceReader;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
        sourceReader = new SendMessageSourceReader();
        XmlMapper xmlMapper = new DefaultXmlMapper();
    }



    @Test
    public void  it_provide_Template_Message_Content_from_msgWithParam() {
        String content = sourceReader.extractMessage(msgWithParams());
        assert(!content.isEmpty());
        assert(content.contains("xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\""));
    }


    @Ignore
    @Test
    public void it_return_asPayload_true_when_exist() {
        assert(false);
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