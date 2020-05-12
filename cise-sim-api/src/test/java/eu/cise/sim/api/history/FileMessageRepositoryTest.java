package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;

import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileMessageRepositoryTest {

    private static Path tempDirWithPrefix;

    private final XmlMapper xmlMapper;
    private final FileMessageRepository fileMessageRepository;

    public FileMessageRepositoryTest() {

        this.xmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        String repositoryDir = tempDirWithPrefix.toString();
        this.fileMessageRepository = new FileMessageRepository(xmlMapper, repositoryDir);

    }

    @BeforeClass
    public static void createTempDir() throws Exception {
         tempDirWithPrefix = Files.createTempDirectory("cise-sim");

    }

    @AfterClass
    public static void removeTempDir() throws IOException {

        FileMessageRepository localRepository =  new FileMessageRepository(new DefaultXmlMapper.PrettyNotValidating(), tempDirWithPrefix.toString());
        localRepository.buildCache();
        FileMessageRepository.MessageStack cache = localRepository.getCache();
        assertNotNull(cache);

        Files.list(tempDirWithPrefix).forEach(x -> x.toFile().delete());
        tempDirWithPrefix.toFile().delete();
    }

    @Test
    public void it_msg_request_recv() throws IOException {

        String message =  readResource("messages/Pull_requestTemplate.xml");
        assertNotNull(message);

        Message pullReq = xmlMapper.fromXML(message);
        fileMessageRepository.messageReceived(pullReq);
        String lastXmlMessage = readLastXml();
        assertNotNull(lastXmlMessage);

        Message pullReqRead = xmlMapper.fromXML(lastXmlMessage);
        assertEquals(pullReqRead, pullReq);
    }

    @Test
    public void it_msg_request_sent() throws IOException {

        String message =  readResource("messages/AckAsync_PullRequestTemplate.xml");
        assertNotNull(message);

        Message pullReq = xmlMapper.fromXML(message);
        fileMessageRepository.messageSent(pullReq);
        String lastXmlMessage = readLastXml();
        assertNotNull(lastXmlMessage);

        Message pullReqRead = xmlMapper.fromXML(lastXmlMessage);
        assertEquals(pullReqRead, pullReq);
    }

    public void it_read_cache() {
        fileMessageRepository.buildCache();
        FileMessageRepository.MessageStack cache = fileMessageRepository.getCache();
        assertNotNull(cache);
    }

    private String readLastXml() throws IOException {

        String xmlMessage = null;

        Path dir = tempDirWithPrefix;  // specify your directory

        Optional<Path> lastFilePath = Files.list(dir)    // here we get the stream with full directory listing
                .filter(f -> !Files.isDirectory(f))  // exclude subdirectories from listing
                .max(Comparator.comparingLong(f -> f.toFile().lastModified()));  // finally get the last file using simple comparator by lastModified field

        if ( lastFilePath.isPresent() ) {
            xmlMessage = Files.readString(lastFilePath.get(), StandardCharsets.UTF_8);
        }

        return xmlMessage;

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
