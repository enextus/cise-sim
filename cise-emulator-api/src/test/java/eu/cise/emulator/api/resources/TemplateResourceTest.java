package eu.cise.emulator.api.resources;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.TemplateAPI;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
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
            .addResource(new TemplateResource(messageAPI, new TemplateAPI(null, null, null), emuConfig))
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {

        File resourcesDirectory = new File("src/test/resources");
        //String fileDir = getFileFromURL().getAbsolutePath();
        //when(emuConfig.templateMessagesDirectory()).thenReturn(fileDir);
        //new File(this.getClass().getResource("/templateDir/").getFile()).;
        //File folder = new File(path);
        //File[] listOfFiles = folder.listFiles();
    }

    @Ignore
    @Test
    public void it_retrieve_list() {
        Response resourceResponse = resources.target("/api/templates")
                .request()
                .get();

        Object listFiles = resourceResponse.getEntity();

        assertThat(listFiles instanceof List);
    }

    private File getFilePath() {
        URL url = this.getClass().getClassLoader().getResource("/templateDir");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }

}