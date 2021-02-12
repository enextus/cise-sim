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

package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.cise.sim.utils.Pair;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.PushBuilder.newPush;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageAPISendTest {

    private MessageProcessor messageProcessor;
    private TemplateLoader templateLoader;
    private Push pushMessage;
    private Acknowledgement ackMessage;
    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;
    private XmlMapper concreteNotValidatingXmlMapper;

    @Before
    public void before() {
        xmlMapper = mock(XmlMapper.class);
        concreteNotValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        jsonMapper = new ObjectMapper();
        messageProcessor = mock(MessageProcessor.class);
        templateLoader = mock(TemplateLoader.class);
        pushMessage = newPush().build();
        ackMessage = newAck().build();
    }

    @Test
    public void it_returns_a_messageApiDto_with_the_acknowledge_received_on_successful_send() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor, templateLoader, xmlMapper, concreteNotValidatingXmlMapper);

        Template loadedTemplate = mock(Template.class);
        when(templateLoader.loadTemplate(any())).thenReturn(loadedTemplate);
        when(messageProcessor.send(any(), any())).thenReturn(new Pair(ackMessage, pushMessage));
        when(xmlMapper.fromXML(any())).thenReturn(pushMessage);
        String ackAsString = concreteNotValidatingXmlMapper.toXML(ackMessage);
        when(xmlMapper.toXML(any())).thenReturn(ackAsString);

        ResponseApi<MessageResponse> sendResponse = messageAPI.send("template-id", msgParams());
        String ackResponseString = concreteNotValidatingXmlMapper.toXML(sendResponse.getResult().getAcknowledgement());
        assertThat(ackResponseString).isEqualTo(ackAsString);
    }

    @Ignore
    @Test
    public void it_returns_a_messageApiDto_with_the_message_sent_on_successful_send() {
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor,  templateLoader, xmlMapper, concreteNotValidatingXmlMapper);

        Template loadedTemplate = mock(Template.class);
        when(templateLoader.loadTemplate(any())).thenReturn(loadedTemplate);
        when(messageProcessor.send(any(), any())).thenReturn(new Pair(ackMessage, pushMessage));
        when(xmlMapper.fromXML(any())).thenReturn(pushMessage);
        String messageAsString = concreteNotValidatingXmlMapper.toXML(ackMessage);
        when(xmlMapper.toXML(any())).thenReturn(messageAsString);

        ResponseApi<MessageResponse> sendResponse = messageAPI.send("template-id", msgParams());
        String msgResponseString = concreteNotValidatingXmlMapper.toXML(sendResponse.getResult().getMessage());

        assertThat(msgResponseString).isEqualTo(messageAsString);
    }

    private JsonNode msgParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        msgTemplateWithParamObject.put("requiresAck", true);
        msgTemplateWithParamObject.put("messageId", "1234-123411-123411-1234");
        msgTemplateWithParamObject.put("correlationId", "7777-666666-666666-5555");

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}
