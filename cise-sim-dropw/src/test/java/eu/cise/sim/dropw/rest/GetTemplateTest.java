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

package eu.cise.sim.dropw.rest;

import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.sim.api.APIError;
import eu.cise.sim.api.DefaultTemplateAPI;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.dropw.restresources.TemplateResource;
import eu.cise.sim.templates.Template;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Date;

import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetTemplateTest {

    private static final DefaultTemplateAPI DEFAULT_TEMPLATE_API = mock(DefaultTemplateAPI.class);
    private static final MessageAPI messageAPI = mock(MessageAPI.class);
    private static final SimConfig SIM_CONFIG = mock(SimConfig.class);

    private XmlMapper xmlMapper;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, DEFAULT_TEMPLATE_API))
            .bootstrapLogging(false)
            .build();
    private Template expectedTemplate;

    @Before
    public void before() {
        xmlMapper = new DefaultXmlMapper();
        expectedTemplate = new Template("template-id-#1", "name-#1");
        when(DEFAULT_TEMPLATE_API.preview(any())).thenReturn(new ResponseApi<Template>(expectedTemplate));
    }

    @After
    public void after() {
        reset(DEFAULT_TEMPLATE_API);
        reset(messageAPI);
    }

    @Test
    public void it_checks_that_the_api_templates_route_exists() {
        Response response = resources.target("/ui/templates/1234567")
                .queryParam("requiresAck", false)
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .request().get();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void it_invokes_the_api_templates_for_preview() {
        resources.target("/ui/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        verify(DEFAULT_TEMPLATE_API).preview(any(TemplateParams.class));
    }

    @Test
    public void it_invokes_the_api_templates_for_preview_with_a_valued_templateParams() {
        resources.target("/ui/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        verify(DEFAULT_TEMPLATE_API).preview(aTemplateParams());
    }

    @Test
    public void it_returns_a_template_when_previewResponse_is_ok() {
        Response response = resources.target("/ui/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        Template actualTemplate = response.readEntity(Template.class);

        assertThat(actualTemplate).isEqualTo(expectedTemplate);
    }

    @Test
    public void it_returns_a_apiError_when_previewResponse_is_ko() {
        when(DEFAULT_TEMPLATE_API.preview(any())).thenReturn(new  ResponseApi<Template>(ResponseApi.ErrorId.FATAL, "exception"));

        Response response = resources.target("/ui/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        APIError actualApiError = response.readEntity(APIError.class);

        APIError expectedApiError = new APIError("exception");
        assertThat(actualApiError).isEqualTo(expectedApiError);
    }

    @Test
    public void it_returns_template_which_contains_the_template_body_as_a_string() {
        Message fakePreparedMessage = newPush()
                .id("mesageId")
                .correlationId("correlation-id")
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .informationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED)
                .informationSensitivity(InformationSensitivityType.GREEN)
                .setEncryptedPayload("false")
                .isPersonalData(false)
                .purpose(PurposeType.NON_SPECIFIED)
                .sender(newService().id("service-id").operation(ServiceOperationType.PUSH).build())
                .build();

        Template template = new Template("template-id-#1", "name-#1", xmlMapper.toXML(fakePreparedMessage));
        when(DEFAULT_TEMPLATE_API.preview(any())).thenReturn(new ResponseApi<Template>(template));
        Response response = resources.target("/ui/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        Template actualTemplate = response.readEntity(Template.class);

        assertThat(actualTemplate.getTemplateContent()).isInstanceOf(String.class);
    }


   @Test
    public void it_returns_template_which_contains_the_template_body_an_xml() {
        Message fakePreparedMessage = newPush()
                .id("messageId")
                .correlationId("correlation-id")
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .informationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED)
                .informationSensitivity(InformationSensitivityType.GREEN)
                .setEncryptedPayload("false")
                .isPersonalData(false)
                .purpose(PurposeType.NON_SPECIFIED)
                .sender(newService().id("service-id").operation(ServiceOperationType.PUSH).build())
                .build();

        Template template = new Template("template-id-#1", "name-#1", xmlMapper.toXML(fakePreparedMessage));

        when(DEFAULT_TEMPLATE_API.preview(any())).thenReturn(new ResponseApi<Template>(template));

        Response response = resources.target("/ui/templates/1234567")
                .queryParam("messageId", "message-id-#1")
                .queryParam("correlationId", "correlation-id-#1")
                .queryParam("requiresAck", false)
                .request().get();

        Template actualTemplate = response.readEntity(Template.class);
        String expectedXMLMessage = xmlMapper.toXML(fakePreparedMessage);
        String actualXMLMessage = actualTemplate.getTemplateContent();
        assertThat(actualXMLMessage).isEqualTo(expectedXMLMessage);
    }


    private TemplateParams aTemplateParams() {
        return new TemplateParams("1234567", "message-id-#1", "correlation-id-#1", false);
    }

}
