package eu.cise.sim.api.dto;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessageShortInfoDtoTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void it_pull_request() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/Pull_requestTemplate.xml");
        assertNotNull(message);

        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(message, isSent);
        assertNotNull(messageShortInfoDto);
        assertEquals("PULL_REQUEST", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_pull_response() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/Pull_responseTemplate.xml");
        assertNotNull(message);

        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(message, isSent);
        assertNotNull(messageShortInfoDto);
        assertEquals("PULL_RESPONSE", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_push_eulsa2() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/PushTemplateEULSA1.xml");
        assertNotNull(message);

        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(message, isSent);
        assertNotNull(messageShortInfoDto);
        assertEquals("PUSH", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_push_tosim2() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/PushTemplateToSim2.xml");
        assertNotNull(message);

        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(message, isSent);
        assertNotNull(messageShortInfoDto);
        assertEquals("PUSH", messageShortInfoDto.getMessageType());
        assertEquals("VesselService", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_ack_synch_pullrequest() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/AckSync_PullRequestTemplate.xml");
        assertNotNull(message);

        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(message, isSent);
        assertNotNull(messageShortInfoDto);
        assertEquals("ACKNOWLEDGEMENT", messageShortInfoDto.getMessageType());
        assertEquals("", messageShortInfoDto.getServiceType());
    }

    @Test
    public void it_ack_asynch_pullrequest() throws IOException {

        boolean isSent = Boolean.TRUE;
        String message =  readResource("messages/AckAsync_PullRequestTemplate.xml");
        assertNotNull(message);

        MessageShortInfoDto messageShortInfoDto = MessageShortInfoDto.getInstance(message, isSent);
        assertNotNull(messageShortInfoDto);
        assertEquals("ACKNOWLEDGEMENT", messageShortInfoDto.getMessageType());
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