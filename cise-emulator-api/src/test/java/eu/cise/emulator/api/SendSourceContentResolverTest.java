package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.helpers.DefaultTemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SendSourceContentResolverTest {
    private DefaultTemplateLoader sourceReader;
    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;
    private EmuConfig emuConfig;

    @Before
    public void before() {
        ClassLoader classLoader = getClass().getClassLoader();
        File folder = new File(classLoader.getResource("templateDir").getFile());
        System.out.println();
        emuConfig = mock(EmuConfig.class);
        when(emuConfig.templateMessagesDirectory()).thenReturn(folder.getAbsolutePath() + File.separator);
        jsonMapper = new ObjectMapper();
        sourceReader = new DefaultTemplateLoader(emuConfig);
        xmlMapper = new DefaultXmlMapper();
    }


    @Test
    public void it_provide_Message_Content_from_msgWithParam() {
        String content = sourceReader.resolveMessage(msgWithParams());
        assertThat(content).contains("xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\"");
    }

    private JsonNode msgWithParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        ObjectNode params = jsonMapper.createObjectNode();
        params.put("requires_ack", true);
        params.put("message_id", "1234-123411-123411-1234");
        params.put("correlation_id", "7777-666666-666666-5555");

        msgTemplateWithParamObject.put("message_template", "COM_01_Vessel_a.xml");
        msgTemplateWithParamObject.set("params", params);

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}