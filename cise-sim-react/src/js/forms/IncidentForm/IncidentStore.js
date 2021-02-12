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

import {getValuesIncident, sendIncidentMessage} from "./IncidentService";
import UserVesselInput from "./inputs/UserVesselInput";
import UserIncidentInput from "./inputs/UserIncidentInput";
import UserContentInput from "./inputs/UserContentInput";
import {buildValueLabelMap} from "../CommonComponents/HelperFunctions";

export default class IncidentStore {

    /**
     * Init phase, retrieving values infos from backend
     */
    async getLabels() {

        console.log("IncidentStore initialization Starting ...");

        const labelsIncidentDto = await getValuesIncident();

        this.setValueAndLabelOnIncidentAndSubtype(labelsIncidentDto.incidentList);
        this.setValueAndLabelOnVesselAndRole(labelsIncidentDto.vessel);

        console.log("IncidentStore initialization done");
    }


    /**
     * Values and Labels build
     */
    labelIncidentType = []; // Single array of string with the Incident Type:
    labelIncidentSubTypeList = [];
    labelVesselTypeList = [];
    labelRoleList = [];

    setValueAndLabelOnIncidentAndSubtype(labelIncidentTypeArray) {

        let tmpType = [];
        for (let labelIncidentType of labelIncidentTypeArray) {
            tmpType.push(labelIncidentType.type);
            this.labelIncidentSubTypeList[labelIncidentType.type] = buildValueLabelMap(labelIncidentType.subTypeList);
        }
        this.labelIncidentType = buildValueLabelMap(tmpType);
    }


    setValueAndLabelOnVesselAndRole(labelVessel) {
        this.labelVesselTypeList = buildValueLabelMap(labelVessel.typeList);
        this.labelRoleList = buildValueLabelMap(labelVessel.roleList);
    }

    //--------------------------------------------

    /**
     * Store Input
     */

    incidentInputInfo = new UserIncidentInput();
    getIncidentInputInfo() {
        return this.incidentInputInfo;
    }

    vesselInputArray = [];
    getVesselInputArrayItem(idx) {
        if (!this.vesselInputArray[idx]) {
            this.vesselInputArray[idx] = new UserVesselInput();
        }
        return this.vesselInputArray[idx];
    }

    contentInputArray = [];
    getContentInputArrayItem(idx) {
        if (!this.contentInputArray[idx]) {
            this.contentInputArray[idx] = new UserContentInput();
        }
        return this.contentInputArray[idx];
    }

    cleanResources() {
        this.incidentInputInfo = new UserIncidentInput();
        this.vesselInputArray = [];
        this.contentInputArray = [];
    }

    //--------------------------------------------

    sendIncidentMessage(msg) {
        return sendIncidentMessage(msg);
    }

}