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
        XML_MAPPER = new DefaultXmlMapper.PrettyNotValidating();
    }

    @Test
    public void it_pull_request() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/Pull_requestTemplate.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Pull Request", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_pull_response() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/Pull_responseTemplate.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Pull Response", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_push_eulsa2() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/PushTemplateEULSA1.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Push", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_push_tosim2() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/PushTemplateToSim2.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Push", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_feedback() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/Feedback_Template.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Feedback", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_unscribe() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/vessel_unsubscribe.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Pull Request", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_subscribe() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/SubscribeTemplate.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Push", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_ack_synch_pullrequest() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/AckSync_PullRequestTemplate.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Ack Synch", messageShortInfoDto.getMessageType());
        assertEquals("", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_ack_asynch_pullrequest() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/AckAsync_PullRequestTemplate.xml");
        assertNotNull(message);

        Message ciseMessage = XML_MAPPER.fromXML(message);
        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(ciseMessage, isSent, new Date(), uuid);
        assertNotNull(messageShortInfoDto);
        assertEquals("Ack Synch", messageShortInfoDto.getMessageType());
        assertEquals("", messageShortInfoDto.getServiceType());
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