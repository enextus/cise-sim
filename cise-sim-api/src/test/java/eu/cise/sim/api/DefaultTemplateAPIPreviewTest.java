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

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.exceptions.NullSendParamEx;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.templates.DefaultTemplateLoader;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.XmlMapper;
// junit 4.13.2
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DefaultTemplateAPIPreviewTest {

    public static MessageProcessor messageProcessor;
    public static MessageStorage messageStorage;
    private TemplateAPI defaultTemplateAPI;
    private TemplateParams templateParams;
    private TemplateLoader templateLoader;
    private XmlMapper xmlMapper;


    @Before
    public void before() {
        xmlMapper = mock(XmlMapper.class);
        templateLoader = mock(DefaultTemplateLoader.class);
        messageProcessor = mock(MessageProcessor.class);
        messageStorage = mock(MessageStorage.class);
        defaultTemplateAPI = new DefaultTemplateAPI(messageProcessor, templateLoader, xmlMapper, xmlMapper);
        templateParams = new TemplateParams("template-id", "message-id", "correlation-id", false);
        when(messageProcessor.preview(any(), any())).thenReturn(mock(Message.class));
        when(templateLoader.loadTemplate(any())).thenReturn(mock(Template.class));
    }

/*     public PreviewResponse preview(TemplateParams templateParams) {
        try {
            Template template = loadTemplate(templateParams.getTemplateId())
            Message message = messaageProcessor(template.getMessage(), templateParams.getSendParams());
            return new OkPreviewResponse(new Template(template.templateId, temnplate.name, message));

        } catch(Exception e) {
            return new KoPreviewResponse(e.getMessage(), templateParams);
        }
    }*/


    @Test
    public void it_returns_a_previewResponse_successfully() {
        ResponseApi<Template> previewResponse = defaultTemplateAPI.preview(templateParams);
        assertThat(previewResponse.isOk()).isTrue();
    }

    @Test
    public void it_returns_with_an_error() {
        when(messageProcessor.preview(any(), any())).thenThrow(NullSendParamEx.class);
        ResponseApi<Template> previewResponse = defaultTemplateAPI.preview(templateParams);
        assertThat(previewResponse.isOk()).isFalse();
    }

    @Test
    public void it_calls_the_messageProcecssor_to_prepare_the_message() {
        defaultTemplateAPI.preview(templateParams);
        verify(messageProcessor).preview(any(), any());
    }

    @Test
    public void it_loads_the_message_to_pass_to_the_message_processor() {
        defaultTemplateAPI.preview(templateParams);
        verify(templateLoader).loadTemplate(any());
    }

    @Test
    public void it_pass_the_parameters_to_the_messageProcessor() {
        when(xmlMapper.fromXML(any())).thenReturn(mock(Message.class));
        defaultTemplateAPI.preview(templateParams);
        SendParam sendParams = new SendParam(templateParams.isRequestAck(), templateParams.getMessageId(), templateParams.getCorrelationId());

        verify(messageProcessor).preview(any(), eq(sendParams));
    }
}

