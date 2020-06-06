import {getvaluesIncident, sendIncidentMessage} from "./IncidentService";
import UserVesselInput from "./UserVesselInput";
import UserIncidentInput from "./UserIncidentInput";

export default class IncidentStore {

    /**
     * Init phase, retrieving values infos from backend
     */
    async getLabels() {

        console.log("IncidentStore initialization Starting ...");

        const labelsIncidentDto = await getvaluesIncident();

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
            //   this.labelIncidentType.push(labelIncidentType.type);
            tmpType.push(labelIncidentType.type);
            this.labelIncidentSubTypeList[labelIncidentType.type] = this.buildValueLabelMap(labelIncidentType.subTypeList);
        }
        this.labelIncidentType = this.buildValueLabelMap(tmpType);
    }

    setValueAndLabelOnVesselAndRole(labelVessel) {
        this.labelVesselTypeList = this.buildValueLabelMap(labelVessel.typeList);
        this.labelRoleList = this.buildValueLabelMap(labelVessel.roleList);
    }

    buildValueLabelMap(valueList) {
        let labelMap = [];
        for (let val of valueList) {
            let lab = val[0] + val.substring(1).replace(/[A-Z][a-z]*/g, str => ' ' + str.toLowerCase());
            labelMap.push({value:val, label:lab});
        }
        return labelMap
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

    //--------------------------------------------

    sendIncidentMessage(msg) {
        return sendIncidentMessage(msg);
    }

}