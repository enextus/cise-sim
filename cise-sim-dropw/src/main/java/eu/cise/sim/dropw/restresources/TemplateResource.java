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

package eu.cise.sim.dropw.restresources;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.MessageResponse;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.TemplateAPI;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.dropw.helpers.BuildHelper;
import eu.cise.sim.templates.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/ui/templates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TemplateResource {

    private final Logger logger;

    private final MessageAPI  messageAPI;
    private final TemplateAPI templateAPI;

    public TemplateResource(MessageAPI messageAPI, TemplateAPI templateAPI) {
        this.messageAPI = messageAPI;
        this.templateAPI = templateAPI;
        this.logger = LoggerFactory.getLogger(TemplateResource.class);
    }

    @GET
    public Response getTemplates() {
        logger.debug("requesting messages templates to the server");
        ResponseApi<List<Template>> templateListResponse = templateAPI.getTemplates();
        return BuildHelper.buildResponse(templateListResponse, Response.Status.INTERNAL_SERVER_ERROR);
    }


    @GET
    @Path("{templateId}")
    public Response getTemplateById(
        @PathParam("templateId")     String templateId,
        @QueryParam("messageId")     String messageId,
        @QueryParam("correlationId") String correlationId,
        @QueryParam("requestAck")    boolean requestAck) {

        ResponseApi<Template> previewResponse = templateAPI.preview(new TemplateParams(templateId, messageId, correlationId, requestAck));
        return  BuildHelper.buildResponse(previewResponse, Response.Status.INTERNAL_SERVER_ERROR);
    }

    @POST
    @Path("{templateId}")
    public Response send(@PathParam("templateId") String templateId, JsonNode msgWithParams) {

        logger.debug("sending template: {} with param: {}", templateId, msgWithParams);
        ResponseApi<MessageResponse> sendResponse = messageAPI.send(templateId, msgWithParams);
        return BuildHelper.buildResponse(sendResponse, Response.Status.INTERNAL_SERVER_ERROR, Response.Status.CREATED);
    }

}
