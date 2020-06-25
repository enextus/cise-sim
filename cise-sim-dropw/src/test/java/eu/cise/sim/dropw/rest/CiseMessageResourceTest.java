package eu.cise.sim.dropw.rest;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.dropw.resources.MessageResource;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;

import javax.ws.rs.core.Response;

import static eu.eucise.helpers.AckBuilder.newAck;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CiseMessageResourceTest {
    private static final MessageAPI messageAPI = mock(MessageAPI.class);


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MessageResource(messageAPI))
            .bootstrapLogging(false)
            .build();

    // todo to understand how work smokito
    //@Test
    public void it_invokes_the_send_the_http_is_successful_201() {
        String message = MessageBuilderUtil.TEST_MESSAGE_XML;
        XmlMapper xmlMapper = new DefaultXmlMapper();
        Message msg = xmlMapper.fromXML(message);

        Acknowledgement acknowledgement = newAck().build();
        when(messageAPI.receive(msg).getResult()).thenReturn(acknowledgement);

        MessageResource ciseMessageResource = new MessageResource(messageAPI);
        Response response = ciseMessageResource.receive(message);
        assertThat(response.getStatus()).isEqualTo(201);
    }


}
