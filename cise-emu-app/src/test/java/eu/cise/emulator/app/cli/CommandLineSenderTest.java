package eu.cise.emulator.app.cli;

import com.github.tomakehurst.wiremock.client.WireMock;
import eu.cise.emulator.app.CiseEmuApplication;
import org.junit.Before;
import org.junit.Test;


public class CommandLineSenderTest {


    private WireMock mockCiseNode;
    private String simappPropertiesPath;
    private String messagePushPath;

    @Before
    public void setup() {
        mockCiseNode = new WireMock("http", "localhost", 64738);

        simappPropertiesPath = getClass().getResource("/simapp.properties").getPath();
        messagePushPath = getClass().getResource("/AisModifiedPush.xml").getPath();
    }


    @Test
    public void is_sending_a_message_from_cli() throws Exception {
        CiseEmuApplication.main(new String[]{"sender", "-c" , simappPropertiesPath,"-s", messagePushPath});
    }

}
