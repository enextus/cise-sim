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

package eu.cise.sim.api.messages.dto.discovery;

import java.io.Serializable;

public class DiscoveryRequestDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private String country;
    private String seaBasin;
    private String serviceType;
    private String serviceOperation;
    private String serviceRole;

    private String discoverySender;
    private String discoveryServiceType;
    private String discoveryServiceOperation;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSeaBasin() {
        return seaBasin;
    }

    public void setSeaBasin(String seaBasin) {
        this.seaBasin = seaBasin;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(String serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public String getServiceRole() {
        return serviceRole;
    }

    public void setServiceRole(String serviceRole) {
        this.serviceRole = serviceRole;
    }

    public String getDiscoverySender() {
        return discoverySender;
    }

    public void setDiscoverySender(String discoverySender) {
        this.discoverySender = discoverySender;
    }

    public String getDiscoveryServiceType() {
        return discoveryServiceType;
    }

    public void setDiscoveryServiceType(String discoveryServiceType) {
        this.discoveryServiceType = discoveryServiceType;
    }

    public String getDiscoveryServiceOperation() {
        return discoveryServiceOperation;
    }

    public void setDiscoveryServiceOperation(String discoveryServiceOperation) {
        this.discoveryServiceOperation = discoveryServiceOperation;
    }

    @Override
    public String toString() {
        return "Country [" + this.country
                + "] Sea Basin [" + this.seaBasin
                + "] Service Type [" + this.serviceType
                + "] Service Operation [" + this.serviceOperation
                + "] Service Role [" + this.serviceRole
                + "] Discovery Consumer [" + this.discoverySender
                + "] Discovery ServiceType [" + this.discoveryServiceType
                + "]";
    }
}
