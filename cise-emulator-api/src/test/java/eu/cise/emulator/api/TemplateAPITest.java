package eu.cise.emulator.api;

import eu.cise.emulator.api.helpers.TemplateLoader;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

}