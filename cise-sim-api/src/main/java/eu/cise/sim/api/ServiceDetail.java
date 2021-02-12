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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.sim.engine.DispatcherType;

@JsonInclude(Include.NON_NULL)
public class ServiceDetail {

    @JsonProperty("serviceParticipantId")
    private final String serviceParticipantId;

    @JsonProperty("serviceTransportMode")
    private final String serviceTransportMode;

    @JsonProperty("endpointUrl")
    private final String endpointUrl;

    @JsonProperty("appVersion")
    private final String appVersion;

    @JsonProperty("messageHistoryMaxLength")
    private final int maxHistoryMsg;

    @JsonProperty("showIncident")
    private final boolean showIncident;

    @JsonProperty("discoverySender")
    private final String discoverySender;

    @JsonProperty("discoveryServiceType")
    private final String discoveryServiceType;

    @JsonProperty("discoveryServiceOperation")
    private final String discoveryServiceOperation;


    public ServiceDetail(Service service,
                         DispatcherType transportMode,
                         String endpointUrl,
                         String appVersion,
                         int maxHistoryMsg,
                         boolean showIncident,
                         String discoverySender,
                         String discoveryServiceType,
                         String discoveryServiceOperation) {
        this.serviceParticipantId =
            (service.getParticipant() != null) ? service.getParticipant().getId() : null;
        this.serviceTransportMode = transportMode.toString();
        this.endpointUrl = endpointUrl;
        this.appVersion = appVersion;
        this.maxHistoryMsg = maxHistoryMsg;
        this.showIncident = showIncident;

        this.discoverySender = discoverySender;
        this.discoveryServiceType = discoveryServiceType;
        this.discoveryServiceOperation = discoveryServiceOperation;
    }

    public String getServiceParticipantId() {
        return serviceParticipantId;
    }

    public String getServiceTransportMode() {
        return serviceTransportMode;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public int getMaxHistoryMsg() {
        return maxHistoryMsg;
    }

    public boolean isShowIncident() {
        return showIncident;
    }

    public String getDiscoverySender() {
        return discoverySender;
    }

    public String getDiscoveryServiceType() {
        return discoveryServiceType;
    }

    public String getDiscoveryServiceOperation() {
        return discoveryServiceOperation;
    }
}
