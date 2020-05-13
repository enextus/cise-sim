package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;

import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class FileMessageRepositoryTest {

    private static Path tempDirWithPrefix;

    private static int TEST_CACHE_DIM = 3;

    private final XmlMapper xmlMapper;
    private final FileMessageRepository fileMessageRepository;

    public FileMessageRepositoryTest() {

        this.xmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        String repositoryDir = tempDirWithPrefix.toString();
        this.fileMessageRepository = new FileMessageRepository(xmlMapper, repositoryDir, TEST_CACHE_DIM);

    }

    @BeforeClass
    public static void createTempDir() throws Exception {
         tempDirWithPrefix = Files.createTempDirectory("cise-sim");
    }

    @AfterClass
    public static void removeTempDir() throws IOException {

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

   @Test
    public void io_full_test() throws IOException {

        int startNumFiles = countFiles();

        String message1 =  readResource("messages/AckAsync_PullRequestTemplate.xml");
        String message2 =  readResource("messages/Pull_requestTemplate.xml");
        String message3 =  readResource("messages/Pull_responseTemplate.xml");
        String message4 =  readResource("messages/PushTemplateEULSA1.xml");
        String message5 =  readResource("messages/PushTemplateToSim2.xml");

        Message ciseMsg1 = xmlMapper.fromXML(message1);
        fileMessageRepository.messageReceived(ciseMsg1);

        Message ciseMsg2 = xmlMapper.fromXML(message2);
        fileMessageRepository.messageReceived(ciseMsg2);

        Message ciseMsg3 = xmlMapper.fromXML(message3);
        fileMessageRepository.messageReceived(ciseMsg3);

        Message ciseMsg4 = xmlMapper.fromXML(message4);
        fileMessageRepository.messageReceived(ciseMsg4);

        Message ciseMsg5 = xmlMapper.fromXML(message5);
        fileMessageRepository.messageReceived(ciseMsg5);

        int endNumFiles = countFiles();

        assertEquals(5, endNumFiles - startNumFiles);

        List<MessageShortInfoDto> shortInfoDtoList =  fileMessageRepository.getShortInfoAfter(0);
        if (TEST_CACHE_DIM <= 5)
            assertEquals(TEST_CACHE_DIM, shortInfoDtoList.size());

        MessageShortInfoDto dto5 = shortInfoDtoList.get(0);
        MessageShortInfoDto dto4 = shortInfoDtoList.get(1);
        MessageShortInfoDto dto3 = shortInfoDtoList.get(2);

        assertEquals(dto5.getId(), ciseMsg5.getMessageID());
        assertEquals(dto4.getId(), ciseMsg4.getMessageID());
        assertEquals(dto3.getId(), ciseMsg3.getMessageID());

        String xmlOnDisk;
        Message messageRead;
        xmlOnDisk = fileMessageRepository.getXmlMessageByUuid(ciseMsg1.getMessageID());
        messageRead = xmlMapper.fromXML(xmlOnDisk);
        assertEquals(messageRead, ciseMsg1);

       xmlOnDisk = fileMessageRepository.getXmlMessageByUuid(ciseMsg2.getMessageID());
       messageRead = xmlMapper.fromXML(xmlOnDisk);
       assertEquals(messageRead, ciseMsg2);

       xmlOnDisk = fileMessageRepository.getXmlMessageByUuid(ciseMsg3.getMessageID());
       messageRead = xmlMapper.fromXML(xmlOnDisk);
       assertEquals(messageRead, ciseMsg3);

       xmlOnDisk = fileMessageRepository.getXmlMessageByUuid(ciseMsg4.getMessageID());
       messageRead = xmlMapper.fromXML(xmlOnDisk);
       assertEquals(messageRead, ciseMsg4);

       xmlOnDisk = fileMessageRepository.getXmlMessageByUuid(ciseMsg5.getMessageID());
       messageRead = xmlMapper.fromXML(xmlOnDisk);
       assertEquals(messageRead, ciseMsg5);


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

    private int countFiles() {
        File[] files = tempDirWithPrefix.toFile().listFiles();
        return files != null ? files.length : 0;
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
