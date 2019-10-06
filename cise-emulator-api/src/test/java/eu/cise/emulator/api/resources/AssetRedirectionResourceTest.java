package eu.cise.emulator.api.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class AssetRedirectionResourceTest {


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AssetRedirectionResource())
            .bootstrapLogging(false)
            .build();

    @Test
    @Ignore
    public void it_always_return_http_temporary_redirect_302() {
        try {
            Response test = resources.target("/").request().get();

            assertThat(test.getStatus()).isEqualTo(302);
        } catch (Exception e) {
            fail("an exception has been thrown instead of redirecting to the /base/index.html");
        }
    }

}
