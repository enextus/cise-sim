package eu.cise.sim.api.resources;

/*import eu.cise.emulator.api.helpers.XmlFileDirectoryList;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
*/
public class WebAPIFilesReferencesResourceTest {

    //File folder;

    /*
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new WebAPIFilesReferencesResource())
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
            //all test request a valid directory path
            folder = new File(this.getClass().getResource("/templateDir/myxml.xml").getFile()).toPath().getParent().toFile();
            System.out.println(folder.getAbsolutePath());

    }


    @Test
    public void it_retreive_3_files_with_in_the_directory() {
        AtomicInteger countFile = new AtomicInteger();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                countFile.getAndIncrement();
            }
        }
        assertThat(countFile.get()).isEqualTo(3);
    }

    @Test
    public void it_retreive_2_xmlfiles_in_the_directory() {

        File[] files;
        AtomicInteger countFilexml = new AtomicInteger();


        try {
            Files.list(Paths.get(folder.getAbsolutePath()))
                    .filter(s -> s.toString().endsWith(".xml"))
                    .sorted()
                    .forEach(e -> countFilexml.getAndIncrement());
        } catch (IOException e) {
            e.printStackTrace();
        };

        assertThat(countFilexml.get()).isEqualTo(2);
    }
    @Test
    public void it_return_a_list_from_non_empty_directory() {

        XmlFileDirectoryList xmlFileDirectoryList= new XmlFileDirectoryList( folder.getAbsolutePath());
        ArrayList<String> listXmlFileNames= xmlFileDirectoryList.getXmlFileNames();
        assertThat(listXmlFileNames).isNotNull();
    }

    @Test
    public void it_return_exact_xmlfile_list_from_test_directory() {

        XmlFileDirectoryList xmlFileDirectoryList= new XmlFileDirectoryList( folder.getAbsolutePath());
        ArrayList<String> listXmlFileNames= xmlFileDirectoryList.getXmlFileNames();
        assertThat(listXmlFileNames).contains("myxml.xml");
        assertThat(listXmlFileNames).contains("my2dxml.xml");
        assertThat(listXmlFileNames.size()).isEqualTo(2);
    }
    */
}
