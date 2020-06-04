package eu.cise.sim.api.rest;

import com.roskart.dropwizard.jaxws.ClientBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import com.roskart.dropwizard.jaxws.JAXWSEnvironment;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.messages.MessageResource;
import eu.cise.sim.io.MessageStorage;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CiseMessageSoapEndpoindTest {
    private static final MessageAPI messageAPI = mock(MessageAPI.class);


    private static MessageStorage messageStorage;
    private JAXWSEnvironment jaxwsEnvironment;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MessageResource(messageAPI, messageStorage))
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
        messageStorage = mock(MessageStorage.class);
        jaxwsEnvironment = mock(JAXWSEnvironment.class);

    }

    @Test
    public void it_invokes_the_service_the_http_is_successful_201() {
        String message = MessageBuilderUtil.TEST_MESSAGE_SOAP;

        MessageResource ciseMessageResource = new MessageResource(messageAPI, messageStorage);
        Response response = ciseMessageResource.receive(message);
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void getClient() {

        JAXWSBundle jaxwsBundle = new JAXWSBundle("/soap", jaxwsEnvironment);

        Class<?> cls = Object.class;
        String url = "http://foo";

        ClientBuilder builder = new ClientBuilder<>(cls, url);
        jaxwsBundle.getClient(builder);
    }

}
