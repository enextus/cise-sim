package eu.cise.emulator.api;

import eu.cise.emulator.api.helpers.TemplateLoader;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TemplateAPITest {

    private TemplateAPI templateAPI;
    private TemplateLoader templateLoader;

    @Before
    public void setUp() throws Exception {
        templateLoader = mock(TemplateLoader.class);
        templateAPI = new TemplateAPI(templateLoader);
    }

    @Test
    public void it_load_the_template_list() {
        templateAPI.getTemplates();

        verify(templateLoader).loadTemplateList();
    }

    @Test
    public void it_should_return_a_response_Ko_when_throwing_an_exception() {
        when(templateLoader.loadTemplateList()).thenThrow(new RuntimeException("badThingsHappens"));

        TemplateListResponse templateListResponse = templateAPI.getTemplates();

        assertThat(templateListResponse).isInstanceOf(TemplateListResponse.KO.class);
    }

}