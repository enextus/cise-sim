import {http_get, http_post} from "../../api/API";
import LabelsIncidentDto from "./dto/LabelsIncidentDto";


export async function getvaluesIncident() {

    const labelsResponse = await http_get("messages/incident/values");
    if (!labelsResponse) return;

    if (labelsResponse.errorCode) {
        console.log("pullMessage returned with n error: ", labelsResponse);
        return labelsResponse;
    }

    console.log("getLabelsIncident done");

    return new LabelsIncidentDto(labelsResponse);
}

export async function sendIncidentMessage(incidentMsg) {
    console.log("sendIncidentMessage");
    const response = await http_post("messages/incident/send", incidentMsg);

    if (response.errorCode) {
        // return Error object
        console.log("sendIncidentMessage retuned with n error: ", response);
    }

    return response;
}