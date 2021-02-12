/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.dto.MessageShortInfoDto;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileMessagePersistenceTest {

    private static Path tempDirWithPrefix;

    private static int TEST_CACHE_DIM = 3;

    private final XmlMapper xmlMapper;
    private final FileMessageService fileMessagePersistence;

    public FileMessagePersistenceTest() {

        this.xmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        String repositoryDir = tempDirWithPrefix.toString();
        this.fileMessagePersistence = new FileMessageService(xmlMapper, repositoryDir, TEST_CACHE_DIM);

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
        fileMessagePersistence.messageReceived(pullReq);
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
        fileMessagePersistence.messageSent(pullReq);
        String lastXmlMessage = readLastXml();
        assertNotNull(lastXmlMessage);

        Message pullReqRead = xmlMapper.fromXML(lastXmlMessage);
        assertEquals(pullReqRead, pullReq);
    }


    @Test
    public void io_full_write_and_reading_sequence() throws IOException {

        int startNumFiles = countFiles();
        List<MessageShortInfoDto> shortInfoDtoList;

        String[] messagFile = { "messages/AckAsync_PullRequestTemplate.xml",
                                "messages/Pull_requestTemplate.xml",
                                "messages/Pull_responseTemplate.xml",
                                "messages/PushTemplateEULSA1.xml",
                                "messages/PushTemplateToSim2.xml"};

        Message[] ciseMsg = new Message[messagFile.length];
        String[]  uuid = new String[messagFile.length];

        for (int i = 0; i < messagFile.length; ++i) {

            String message =  readResource(messagFile[i]);
            ciseMsg[i] = xmlMapper.fromXML(message);
            fileMessagePersistence.messageReceived(ciseMsg[i]);
            shortInfoDtoList =  fileMessagePersistence.getThreadsAfter(0);
            uuid[i]= shortInfoDtoList.get(0).getId();
        };

        int endNumFiles = countFiles();

        assertEquals(messagFile.length, endNumFiles - startNumFiles);

        shortInfoDtoList =  fileMessagePersistence.getThreadsAfter(0);
        if (TEST_CACHE_DIM <= messagFile.length)
            assertEquals(TEST_CACHE_DIM, shortInfoDtoList.size());

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
