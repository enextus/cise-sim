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