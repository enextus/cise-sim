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

package eu.cise.sim.api.messages.dto.label;

import eu.cise.servicemodel.v1.authority.CountryType;
import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryMessageLabelDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private final List<String> countryList;
    private final List<String> seaBasinList;
    private final List<String> serviceTypeList;
    private final List<String> serviceOperationList;
    private final List<String> serviceRoleList;

    private static DiscoveryMessageLabelDto instance;

    static {

        List<String> countryList = new ArrayList<>();
        for (CountryType country : CountryType.values()) {
            countryList.add(country.value());
        }

        List<String> seaBasinList = new ArrayList<>();
        for (SeaBasinType type : SeaBasinType.values()) {
            seaBasinList.add(type.value());
        }

        List<String> serviceTypeList = new ArrayList<>();
        for (ServiceType type : ServiceType.values()) {
            serviceTypeList.add(type.value());
        }

        List<String> serviceOperationList = new ArrayList<>();
        for (ServiceOperationType type : ServiceOperationType.values()) {
            serviceOperationList.add(type.value());
        }

        List<String> serviceRoleList = new ArrayList<>();
        for (ServiceRoleType type : ServiceRoleType.values()) {
            serviceRoleList.add(type.value());
        }

        instance = new DiscoveryMessageLabelDto(countryList, seaBasinList, serviceTypeList, serviceOperationList, serviceRoleList);
    }

    public static DiscoveryMessageLabelDto getInstance() {
        return instance;
    }

    private DiscoveryMessageLabelDto(List<String> countryList, List<String> seaBasinList, List<String> serviceTypeList, List<String> serviceOperationList, List<String> serviceRoleList) {
        this.countryList = countryList;
        this.seaBasinList = seaBasinList;
        this.serviceTypeList = serviceTypeList;
        this.serviceOperationList = serviceOperationList;
        this.serviceRoleList = serviceRoleList;
    }


    public List<String> getCountryList() {
        return countryList;
    }

    public List<String> getSeaBasinList() {
        return seaBasinList;
    }

    public List<String> getServiceTypeList() {
        return serviceTypeList;
    }

    public List<String> getServiceOperationList() {
        return serviceOperationList;
    }

    public List<String> getServiceRoleList() {
        return serviceRoleList;
    }
}
