package eu.cise.sim.api;

import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.exceptions.LoaderEx;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DefaultTemplateAPITest {

    private DefaultTemplateAPI defaultTemplateAPI;
    private TemplateLoader templateLoader;

    @Before
    public void before() {
        templateLoader = mock(TemplateLoader.class);
        defaultTemplateAPI = new DefaultTemplateAPI(
                mock(MessageProcessor.class),
                templateLoader,
                new DefaultXmlMapper(), new DefaultXmlMapper.PrettyNotValidating());
    }

    @Test
    public void it_load_the_template_list() {
        defaultTemplateAPI.getTemplates();

        verify(templateLoader).loadTemplateList();
    }

    @Test
    public void it_returns_a_template_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1", "name-1"),
                new Template("id-2", "name-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        List<Template> actualTemplateList = defaultTemplateAPI.getTemplates().getResult();

        assertThat(actualTemplateList).hasSameElementsAs(expectedTemplateList);
    }

    @Test
    public void it_returns_a_ok_response_when_returning_a_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1", "name-1"),
                new Template("id-2", "name-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        assertThat(defaultTemplateAPI.getTemplates().isOk()).isTrue();
    }

    @Ignore
    @Test
    public void it_returns_a_ko_response_when_throwing_an_IOLoaderException() {
        when(templateLoader.loadTemplateList()).thenThrow(new LoaderEx());

        defaultTemplateAPI.getTemplates();

        assertThat(defaultTemplateAPI.getTemplates().isOk()).isFalse();
    }

}