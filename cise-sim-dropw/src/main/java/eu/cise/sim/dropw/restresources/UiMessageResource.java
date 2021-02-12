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


import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.MessageResponse;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.messages.MessageService;
import eu.cise.sim.api.messages.dto.discovery.DiscoveryRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.dropw.helpers.BuildHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ui/messages")
@Produces(MediaType.APPLICATION_JSON)
public class UiMessageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UiMessageResource.class);
    private final MessageAPI messageAPI;
    private final MessageService messageService;

    public UiMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;
        this.messageService = new MessageService();
    }

    @POST
    @Consumes({"application/xml", "text/plain", "text/xml"})
    @Produces("application/xml")
    public Response receive(String inputXmlMessage) {

        ResponseApi<String> acknowledgement = messageAPI.receiveXML(inputXmlMessage);
        return BuildHelper.buildResponse(acknowledgement, Response.Status.INTERNAL_SERVER_ERROR, Response.Status.CREATED);
    }

    /* ----------- INCIDENT ----------- */

    @Path("/incident/values")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabelsIncident() {

        return Response
                .status(Response.Status.OK)
                .entity(messageService.getLabelsIncident())
                .build();
    }

    @Path("/incident/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendIncident(IncidentRequestDto incidentMsg) {

        LOGGER.info("sendIncident received " + incidentMsg);

        ResponseApi<Message> message = messageService.buildIncident(incidentMsg);
        if (message.isOk()) {
            ResponseApi<MessageResponse> response = messageAPI.send(message.getResult());
        }

        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }

    /* ----------- DISCOVERY ----------- */

    @Path("/discovery/values")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabelsDiscovery() {

        return Response
                .status(Response.Status.OK)
                .entity(messageService.getLabelsDiscovery())
                .build();
    }

    @Path("/discovery/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendDiscovery(DiscoveryRequestDto discoveryMsg) {

        LOGGER.info("sendDiscovery received " + discoveryMsg);

        ResponseApi<Message> message = messageService.buildDiscovery(discoveryMsg);
        if (message.isOk()) {
            ResponseApi<MessageResponse> response = messageAPI.send(message.getResult());

            if (response.isOk()) {
                messageService.manageDiscoveryAnswer(response.getResult().getAcknowledgement());
            }
        }

        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }
}
