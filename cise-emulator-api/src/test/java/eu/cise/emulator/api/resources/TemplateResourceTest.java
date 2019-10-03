package eu.cise.emulator.api.resources;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.TemplateAPI;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateResourceTest {
    private static MessageAPI messageAPI = mock(MessageAPI.class);;
    private static TemplateAPI templateAPI = mock(TemplateAPI.class);;
    private static EmuConfig emuConfig = mock(EmuConfig.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, new TemplateAPI(), emuConfig))
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
        // get the template test folder absolute path
        File templatesMessagesDirectory = new File("src/test/resources/templatesMessages");
        File[] templatesMessagesFiles = templatesMessagesDirectory.listFiles();
        String templatesMessagesDirectoryAbsolutePathPath = templatesMessagesDirectory.getAbsolutePath();

        when(emuConfig.templateMessagesDirectory()).thenReturn(templatesMessagesDirectoryAbsolutePathPath);
    }

    @Test
    public void it_retrieve_list() {

        Response response = resources.target("/api/templates")
                .request()
                .get();
        List<String> listFiles
                = response.readEntity(new GenericType<List<String>>() {});

        boolean resultAcceptedAndContainFiles = response.getStatus() == Response.Status.OK.getStatusCode() &&
                listFiles.size() > 0;

        assertThat(resultAcceptedAndContainFiles);
    }


}