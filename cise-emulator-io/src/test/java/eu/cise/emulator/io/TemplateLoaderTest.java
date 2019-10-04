package eu.cise.emulator.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.exceptions.DirectoryNotFoundEx;
import eu.cise.emulator.templates.DefaultTemplateLoader;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TemplateLoaderTest {

    private TemplateLoader templateLoader;
    private EmuConfig emuConfig;
    private XmlMapper xmlMapper;

    @Before
    public void before() {
        emuConfig = mock(EmuConfig.class);
        xmlMapper = new DefaultXmlMapper();
        templateLoader = new DefaultTemplateLoader(xmlMapper, emuConfig);

        when(emuConfig.messageTemplateDir())
            .thenReturn(getAbsPathFromResourceDir("templateDir"));
    }

    @After
    public void after() {
        reset(emuConfig);
    }

    @Test
    public void it_returns_a_list_of_template_loading_files_in_templates_dir() {
        List<Template> templateList = templateLoader.loadTemplateList();

        assertThat(templateList).isInstanceOf(List.class);
    }

    @Test
    public void it_loads_a_the_three_files_in_templates_dir() {
        List<Template> templateList = templateLoader.loadTemplateList();

        assertThat(templateList).hasSize(3);
    }

    @Test
    public void it_loads_a_file_named_COM_01_Vessel_a_xml() {
        List<Template> templateList = templateLoader.loadTemplateList();

        assertThat(templateList)
            .contains(new Template("COM_01_Vessel_a.xml", "COM_01_Vessel_a.xml"));
    }

    @Test
    public void it_returns_an_exception_when_requested_directory_doesNotExist() {
        when(emuConfig.messageTemplateDir()).thenReturn("not-existing-dir");

        assertThatExceptionOfType(DirectoryNotFoundEx.class)
            .isThrownBy(() -> templateLoader.loadTemplateList());
    }

    private String getAbsPathFromResourceDir(String resourceDir) {
        return new File(getTemplateDirURL(resourceDir)).getAbsolutePath();
    }

    private URI getTemplateDirURL(String resourceDir) {
        try {
            return this.getClass().getClassLoader().getResource(resourceDir).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}