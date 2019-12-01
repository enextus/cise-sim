package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.exceptions.LoaderEx;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TemplateAPITest {

    private TemplateAPI templateAPI;
    private TemplateLoader templateLoader;

    @Before
    public void before() {
        templateLoader = mock(TemplateLoader.class);
        templateAPI = new TemplateAPI(
                mock(MessageProcessor.class),
                templateLoader,
                new DefaultXmlMapper(), new DefaultXmlMapper.PrettyNotValidating());
    }

    @Test
    public void it_load_the_template_list() {
        templateAPI.getTemplates();

        verify(templateLoader).loadTemplateList();
    }

    @Test
    public void it_returns_a_template_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1", "name-1"),
                new Template("id-2", "name-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        List<Template> actualTemplateList = templateAPI.getTemplates().getTemplates();

        assertThat(actualTemplateList).hasSameElementsAs(expectedTemplateList);
    }

    @Test
    public void it_returns_a_ok_response_when_returning_a_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1", "name-1"),
                new Template("id-2", "name-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        assertThat(templateAPI.getTemplates()).isInstanceOf(TemplateListResponse.OK.class);
    }

    @Ignore
    @Test
    public void it_returns_a_ko_response_when_throwing_an_IOLoaderException() {
        when(templateLoader.loadTemplateList()).thenThrow(new LoaderEx());

        templateAPI.getTemplates();

        assertThat(templateAPI.getTemplates()).isInstanceOf(TemplateListResponse.KO.class);
    }


}