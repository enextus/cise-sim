import LabelIncidentType from "./LabelIncidentType";
import LabelVesselDto from "./LabelVesselDto";

export default class LabelsIncidentDto {

    incidentList;
    vessel;

    constructor(props) {

        this.incidentList = [];
        let incident;
        for (incident of props.incidentList) {
            this.incidentList.push(new LabelIncidentType(incident));
        }

        this.vessel = new LabelVesselDto(props.vessel);
    }
}