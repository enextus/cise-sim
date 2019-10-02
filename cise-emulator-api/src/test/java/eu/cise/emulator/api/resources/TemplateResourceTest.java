package eu.cise.emulator.api.resources;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateResourceTest {
    private static MessageAPI messageAPI = mock(MessageAPI.class);;
    private static EmuConfig emuConfig = mock(EmuConfig.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, emuConfig))
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
        when(emuConfig.templateMessagesDirectory()).thenReturn("/home/ciseus07/git/cise-emu/cise-emulator-api/src/test/resources/templateDir");
    }

    @Test
    public void it_retrieve_list() {
        Response resourceResponse = resources.target("/api/templates")
                .request()
                .get();

        Object listFiles = resourceResponse.getEntity();

        assertThat(listFiles instanceof List);
    }
}