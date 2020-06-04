import IncidentMessageDto from "./dto/IncidentMessageDto";
import {getLabelsIncident} from "./IncidentService";
import {action, observable} from "mobx";

export default class IncidentStore {


    async getLabels() {

        console.log("IncidentStore initialization Starting ...");

        const labelsIncidentDto = await getLabelsIncident();

        this.setLabelType(labelsIncidentDto.incidentList);
        this.setLabelVesselAndRole(labelsIncidentDto.vessel);


        console.log("IncidentStore initialization done");
    }

    // Message to be sent
    @observable incidentMessage = new IncidentMessageDto();

    @action
    setIncidentType(type) {
        this.incidentMessage.incidentType = type;
    }

    setSubType(subType) {
        this.incidentMessage.subType = subType;
    }

    setLatitude(latitude) {
        this.incidentMessage.latitude = latitude;
    }

    setLongitude(longitude) {
        this.incidentMessage.longitude = longitude;
    }

    setImoNumber(imoNumber) {
        this.incidentMessage.imoNumber = imoNumber;
    }

    setMmsi(mmsi) {
        this.incidentMessage.mmsi = mmsi;
    }

    setVesselType(vesselType) {
        this.incidentMessage.vesselType = vesselType;
    }

    setRole(role) {
        this.incidentMessage.role = role;
    }

    labelIncidentType = []; // Single array of string with the Incident Type:
    labelIncidentSubTypeList = [];

    setLabelType(labelIncidentTypeArray) {


        let tmpType = [];
        for (let labelIncidentType of labelIncidentTypeArray) {
         //   this.labelIncidentType.push(labelIncidentType.type);
            tmpType.push(labelIncidentType.type);
            this.labelIncidentSubTypeList[labelIncidentType.type] = this.buildValueLabelMap(labelIncidentType.subTypeList);
        }
        this.labelIncidentType = this.buildValueLabelMap(tmpType);
    }

    labelVesselTypeList = [];
    labelRoleList = [];

    setLabelVesselAndRole(labelVessel) {
        this.labelVesselTypeList = this.buildValueLabelMap(labelVessel.typeList);
        this.labelRoleList = this.buildValueLabelMap(labelVessel.roleList);

        console.log("setLabelVessel labelVesselTypeList:"+this.labelVesselTypeList);
        console.log("setLabelVessel labelRoleList:"+this.labelRoleList);
    }

    buildValueLabelMap(valueList) {
        let labelMap = [];
        for (let val of valueList) {
            let lab = val[0] + val.substring(1).replace(/[A-Z][a-z]*/g, str => ' ' + str.toLowerCase());
            labelMap.push({value:val, label:lab});
        }
        return labelMap
    }

    getLabelIncidentType() {
        return this.labelIncidentType;
    }

    getLabelSubType() {
        return this.labelIncidentSubTypeList[this.incidentMessage.incidentType];
    }

    getLabelVesselType() {
        return this.labelVesselTypeList;
    }

    getLabelRole() {
        return this.labelRoleList;
    }


}