package eu.cise.sim.dropw.rest;

import eu.cise.sim.api.APIError;
import eu.cise.sim.api.DefaultTemplateAPI;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.dropw.restresources.TemplateResource;
import eu.cise.sim.templates.Template;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GetTemplatesTest {

    private static final DefaultTemplateAPI DEFAULT_TEMPLATE_API = mock(DefaultTemplateAPI.class);
    private static final MessageAPI messageAPI = mock(MessageAPI.class);
    private static final SimConfig SIM_CONFIG = mock(SimConfig.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, DEFAULT_TEMPLATE_API))
            .bootstrapLogging(false)
            .build();

    private List<Template> expectedTemplateList;

    @Before
    public void before() {
        expectedTemplateList = asList(new Template("id1", "name1"), new Template("id2", "name2"));
        when(DEFAULT_TEMPLATE_API.getTemplates()).thenReturn(new ResponseApi<List<Template>>(expectedTemplateList));
    }

    @After
    public void after() {
        reset(DEFAULT_TEMPLATE_API);
        reset(messageAPI);
    }

    @Test
    public void it_checks_the_route_to_get_templates_exists() {
        Response response = resources.target("/ui/templates").request().get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void it_returns_a_template_list_for_templateListResponse_is_ok() {
        Response response = resources.target("/ui/templates").request().get();

        List<Template> actualTemplateList = response.readEntity(genericTemplateList());

        assertThat(actualTemplateList).hasSameElementsAs(expectedTemplateList);
    }

    @Test
    public void it_returns_an_api_for_templateListResponse_is_ko() {
        when(DEFAULT_TEMPLATE_API.getTemplates()).thenReturn(new ResponseApi<List<Template>>(ResponseApi.ErrorId.FATAL, "exception"));

        Response response = resources.target("/ui/templates").request().get();

        APIError actualApiError = response.readEntity(APIError.class);

        APIError expectedApiError = new APIError("exception");

        assertThat(actualApiError).isEqualTo(expectedApiError);
    }

    @Test
    public void it_checks_HTTP_code_to_server_error_when_templateListResponse_is_ko() {
        when(DEFAULT_TEMPLATE_API.getTemplates()).thenReturn(new ResponseApi<List<Template>>(ResponseApi.ErrorId.FATAL, "exception"));

        Response response = resources.target("/ui/templates").request().get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }


    private GenericType<List<Template>> genericTemplateList() {
        return new GenericType<List<Template>>() {
        };
    }
}
