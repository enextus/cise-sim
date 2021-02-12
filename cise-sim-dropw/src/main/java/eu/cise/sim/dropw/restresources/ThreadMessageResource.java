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


import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.api.history.ThreadMessageAPI;
import eu.cise.sim.dropw.helpers.BuildHelper;
import org.apache.commons.collections.CollectionUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ui/history")
public class ThreadMessageResource {

    private final ThreadMessageAPI threadMessageAPI;

    public ThreadMessageResource(ThreadMessageAPI threadMessageAPI) {
        this.threadMessageAPI = threadMessageAPI;
    }


    @Path("/latest/{timestamp}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestMessages(@PathParam("timestamp") long timestamp) {

        List<MessageShortInfoDto> lastStoredMessage = threadMessageAPI.getThreadsAfter(timestamp);

        Response response;
        if (CollectionUtils.isEmpty(lastStoredMessage)) {
            response = Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(lastStoredMessage)
                    .build();
        }

        return response;
    }

    @Path("/message/{uuid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getMessageByUuid(@PathParam("uuid") String uuid) {

        ResponseApi<String> xmlMessage = threadMessageAPI.getXmlMessageByUuid(uuid);
        return BuildHelper.buildResponse(xmlMessage, Response.Status.NO_CONTENT);
    }
}
