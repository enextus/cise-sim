import {http_get} from "../../api/API";
import LabelsIncidentDto from "./dto/LabelsIncidentDto";


export async function getLabelsIncident() {

    const labelsResponse = await http_get("messages/labels/incident");
    if (!labelsResponse) return;

    if (labelsResponse.errorCode) {
        console.log("pullMessage returned with n error: ", labelsResponse);
        return labelsResponse;
    }

    console.log("getLabelsIncident done");

    return new LabelsIncidentDto(labelsResponse);
}
