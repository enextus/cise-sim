package eu.cise.emulator.api;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TemplateAPITest {

    private TemplateAPI templateAPI;
    private TemplateLoader templateLoader;

    @Before
    public void before() {
        templateLoader = mock(TemplateLoader.class);
        templateAPI = new TemplateAPI(mock(MessageProcessor.class), templateLoader);
    }

    @Test
    public void it_load_the_template_list() {
        templateAPI.getTemplates();

        verify(templateLoader).loadTemplateList();
    }

    @Test
    public void it_returns_a_template_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1"), new Template("id-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        List<Template> actualTemplateList = templateAPI.getTemplates().getTemplates();

        assertThat(actualTemplateList).hasSameElementsAs(expectedTemplateList);
    }

    @Test
    public void it_returns_a_ok_response_when_returning_a_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1"), new Template("id-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        assertThat(templateAPI.getTemplates()).isInstanceOf(TemplateListResponse.OK.class);
    }

    @Test
    public void it_returns_a_ko_response_when_throwing_an_IOLoaderException() {
        when(templateLoader.loadTemplateList()).thenThrow(new IOLoaderException());

        TemplateListResponse templateListResponse = templateAPI.getTemplates();

        assertThat(templateListResponse).isInstanceOf(TemplateListResponse.KO.class);
    }

}