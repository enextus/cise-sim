package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessageShortInfoDtoTest {

    private final String uuid = UUID.randomUUID().toString();

    private static XmlMapper XML_MAPPER;

    @BeforeClass
    public static void createTempDir() throws Exception {
        XML_MAPPER = new DefaultXmlMapper();
    }

    @Test
    public void it_pull_request() throws IOException {

        doTest(  Boolean.TRUE, "messages/Pull_requestTemplate.xml", "Pull Request", "VesselService");
    }

    @Test
    public void it_pull_response() throws IOException {

        doTest(  Boolean.TRUE, "messages/Pull_responseTemplate.xml", "Pull Response", "VesselService");
    }

    @Test
    public void it_push_eulsa2() throws IOException {

        doTest(  Boolean.TRUE, "messages/PushTemplateEULSA1.xml", "Publish", "VesselService");
    }

    @Test
    public void it_push_tosim2() throws IOException {

        doTest(  Boolean.TRUE, "messages/PushTemplateToSim2.xml", "Push", "VesselService");
    }

    @Test
    public void it_feedback() throws IOException {

        doTest(  Boolean.TRUE, "messages/Feedback_Template.xml", "Feedback", "VesselService");
    }

    @Test
    public void it_unscribe() throws IOException {

        doTest(  Boolean.TRUE, "messages/vessel_unsubscribe.xml", "Unsubscribe", "VesselService");
    }

    @Test
    public void it_subscribe() throws IOException {

        doTest(  Boolean.TRUE, "messages/SubscribeTemplate.xml", "Publish", "VesselService");
    }

    @Test
    public void it_ack_synch_pullrequest() throws IOException {

        doTest(  Boolean.TRUE, "messages/AckSync_PullRequestTemplate.xml", "Sync Ack", "");
    }

    @Test
    public void it_ack_asynch_pullrequest() throws IOException {

        doTest(  Boolean.TRUE, "messages/AckAsync_PullRequestTemplate.xml", "Sync Ack", "");
    }

    private void doTest( boolean isSent, String messageFileName, String expectedType, String expectedService) throws IOException {

        String message =  readResource(messageFileName);
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals(expectedType, messageShortInfoDto.getMessageType());
        assertEquals(expectedService, messageShortInfoDto.getServiceType());

    }

    private String readResource(String resourceName) throws IOException {
        Path path = Paths.get(getResourceURI(resourceName));
        return Files.readString(path);
    }

    private URI getResourceURI(String resourceDir) {
        try {
            return this.getClass().getClassLoader().getResource(resourceDir).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}