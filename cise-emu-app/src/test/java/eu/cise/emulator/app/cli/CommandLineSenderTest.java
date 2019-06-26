package eu.cise.emulator.app.cli;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import eu.cise.emulator.app.CiseEmuApplication;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class CommandLineSenderTest {


    private WireMock mockCiseNode;
    private String simappPropertiesPath;
    private String messagePushPath;

    @Rule
    public WireMockRule server = new WireMockRule(WireMockConfiguration.options().port(64738), false);


    @Before
    public void setup() {

        simappPropertiesPath = getClass().getResource("/simapp.properties").getPath();
        messagePushPath = getClass().getResource("/AisModifiedPush.xml").getPath();
    }


    @Test
    public void is_sending_a_message_from_cli() throws Exception {
        server.stubFor(post(urlEqualTo("/mock-node"))
                        .willReturn(aResponse()
                                            .withHeader("Content-Type", "text/xml")
                                            .withBody("<Push />")));


        CiseEmuApplication.main(new String[]{"sender", "-c" , simappPropertiesPath,"-s", messagePushPath});
    }

}
