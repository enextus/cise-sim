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

import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.sim.api.ServiceDetail;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.dropw.context.VersionApp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ui/service")
@Produces(MediaType.APPLICATION_JSON)
public class UIServiceResource {
    private final SimConfig simConfig;

    public UIServiceResource(SimConfig simConfig) {
        this.simConfig = simConfig;
    }

    @GET
    @Path("/self")
    public Response informSelfInfo() {
        VersionApp versionApp = new VersionApp();
        Participant participant = new Participant();
        participant.setId(simConfig.simulatorName());

        Service service = new Service();
        service.setParticipant(participant);

        ServiceDetail serviceDetail = new ServiceDetail(
            service,
            simConfig.destinationProtocol(),
            simConfig.destinationUrl(),
            versionApp.getVersion(),
            simConfig.guiMaxThMsgs(),
            simConfig.showIncident(),
            simConfig.discoverySender(),
            simConfig.discoveryServiceType(),
            simConfig.discoveryServiceOperation());


        return Response
                .status(Response.Status.OK)
                .entity(serviceDetail)
                .build();
    }

}
