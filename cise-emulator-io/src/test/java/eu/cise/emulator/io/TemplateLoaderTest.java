package eu.cise.emulator.io;

import eu.cise.emulator.exceptions.IOLoaderDirectoryEmptyException;
import eu.cise.emulator.exceptions.IOLoaderDirectoryNotFoundException;
import eu.cise.emulator.templates.DefaultTemplateLoader;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateLoaderTest {

    private TemplateLoader templateLoader;

    @Before
    public void before() {
        templateLoader = new DefaultTemplateLoader();
    }


    @Test
    public void it_return_the_filelist_of_templatedir() {
        List<Template> templateList = templateLoader.loadTemplateList();
        assertThat(templateList).isInstanceOf(List.class);
    }

    @Test
    public void it_load_a_nonEmpty_filelist_of_templatedir() {
        List<Template> templateList = templateLoader.loadTemplateList();
        assertThat(templateList.size()).isGreaterThan(0);
    }
    @Ignore
    @Test
    public void it_returns_an_exception_when_requested_directory_doesNotExist() {
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
        Exception eref = new RuntimeException();
        try {
            List<Template> templateList = templateLoader.loadTemplateList();
        } catch (Exception ereal) {
            eref = ereal;
        }
        assertThat(eref).isInstanceOf(IOLoaderDirectoryEmptyException.class);
    }


}