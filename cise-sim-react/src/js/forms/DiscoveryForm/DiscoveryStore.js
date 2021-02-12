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

import {getValuesDiscovery, sendDiscoveryMessage} from "./DiscoveryService";
import DiscoveryMessageDto from "./dto/DiscoveryMessageDto";
import {buildValueLabelMap} from "../CommonComponents/HelperFunctions";


export default class DiscoveryStore {

    /**
     * Init phase, retrieving values infos from backend
     */
    async getLabels() {

        console.log("DiscoveryStore initialization Starting ...");

        const labelsDiscoveryDto = await getValuesDiscovery();

        this.setValueAndLabelOnDiscovery(labelsDiscoveryDto);

        console.log("DiscoveryStore initialization done");
    }


    /**
     * Values and Labels build
     */
    labelCountryList = [];
    labelSeaBasinList = [];
    labelServiceTypeList = [];
    labelServiceOperationList = [];
    labelServiceRoleList = [];

    setValueAndLabelOnDiscovery(labelsDiscoveryDto) {
        this.labelCountryList     = buildValueLabelMap(labelsDiscoveryDto.countryList.sort());
        this.labelSeaBasinList    = buildValueLabelMap(labelsDiscoveryDto.seaBasinList.sort());
        this.labelServiceTypeList = buildValueLabelMap(labelsDiscoveryDto.serviceTypeList.sort());
        this.labelServiceOperationList  = buildValueLabelMap(labelsDiscoveryDto.serviceOperationList.sort());
        this.labelServiceRoleList       = buildValueLabelMap(labelsDiscoveryDto.serviceRoleList.sort());
    }


    //--------------------------------------------

    /**
     * Store Input
     */

    discoveryInputInfo = new DiscoveryMessageDto();
    getDiscoveryInputInfo() {
        return this.discoveryInputInfo;
    }

    cleanResources() {
        this.discoveryInputInfo = new DiscoveryMessageDto();
    }

    //--------------------------------------------

    async sendDiscoveryMessage(sender, type, operation) {
        this.discoveryInputInfo.discoverySender = sender;
        this.discoveryInputInfo.discoveryServiceType = type;
        this.discoveryInputInfo.discoveryServiceOperation = operation;

        return await sendDiscoveryMessage(this.discoveryInputInfo);
    }

}