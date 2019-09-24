package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.helpers.ServerExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultMessageResourceTest {


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new DefaultMessageResource())
            .bootstrapLogging(false)
            .addProvider(new ServerExceptionMapper())
            .build();
    @Ignore
    @Test
    public void it_always_return_http_temporary_redirect_302() {

        try {
            Response test = resources.target("/index.html")
                    .request()
                    .get();
            assertThat(test.getStatus()).isEqualTo(302);
        } catch (Exception e) {
            // do nothing
        }
    }

}
