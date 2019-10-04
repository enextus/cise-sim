package eu.cise.emulator.io;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.exceptions.IOLoaderDirectoryEmptyException;
import eu.cise.emulator.exceptions.IOLoaderDirectoryNotFoundException;
import eu.cise.emulator.templates.DefaultTemplateLoader;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TemplateLoaderTest {

    private TemplateLoader templateLoader;
    private EmuConfig emuConfig;
    @Before
    public void before() {
        emuConfig = mock(EmuConfig.class);


        templateLoader = new DefaultTemplateLoader(emuConfig);
    }


    @Test
    public void it_return_the_filelist_of_templatedir() {
        when(emuConfig.templateMessagesDirectory()).thenReturn(getCorrectFolderFromUrl().getAbsolutePath().toString());

        List<Template> templateList = templateLoader.loadTemplateList();
        assertThat(templateList).isInstanceOf(List.class);
    }

    @Test
    public void it_load_a_nonEmpty_filelist_of_templatedir() {
        when(emuConfig.templateMessagesDirectory()).thenReturn(getCorrectFolderFromUrl().getAbsolutePath().toString());

        List<Template> templateList = templateLoader.loadTemplateList();
        assertThat(templateList.size()).isGreaterThan(0);
    }


    @Test
    public void it_returns_an_exception_when_requested_directory_doesNotExist() {
        when(emuConfig.templateMessagesDirectory()).thenReturn("inexistentTemplateDir");


        Exception eref = new RuntimeException();
        try {
            List<Template> templateList = templateLoader.loadTemplateList();
        } catch (Exception ereal) {
            eref = ereal;
        }
        assertThat(eref).isInstanceOf(IOLoaderDirectoryNotFoundException.class);
    }

    @Ignore
    @Test
    public void it_returns_an_exception_when_requested_directory_isEmpty() {
        when(emuConfig.templateMessagesDirectory()).thenReturn(getIncorrectFolderFromUrl().getAbsolutePath().toString());

        Exception eref = new RuntimeException();
        try {
            List<Template> templateList = templateLoader.loadTemplateList();
        } catch (Exception ereal) {
            eref = ereal;
        }
        assertThat(eref).isInstanceOf(IOLoaderDirectoryEmptyException.class);
    }

    private File getIncorrectFolderFromUrl() {
        URL url = this.getClass().getClassLoader().getResource("templateEmptyDir");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }

    private File getCorrectFolderFromUrl() {
        URL url = this.getClass().getClassLoader().getResource("templateDir");
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