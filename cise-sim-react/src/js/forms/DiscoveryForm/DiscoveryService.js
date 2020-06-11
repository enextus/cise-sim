import {http_get, http_post} from "../../api/API";
import LabelDiscoveryDto from "./dto/LabelDiscoveryDto";


export async function getValuesDiscovery() {

    const labelsResponse = await http_get("messages/discovery/values");
    if (!labelsResponse) return;

    if (labelsResponse.errorCode) {
        console.log("pullMessage returned with n error: ", labelsResponse);
        return labelsResponse;
    }

    console.log("getValuesDiscovery done");

    return new LabelDiscoveryDto(labelsResponse);
}

export async function sendDiscoveryMessage(discoveryMsg) {

    const response = await http_post("messages/discovery/send", discoveryMsg);

    if (response.errorCode) {
        // return Error object
        console.log("sendDiscoveryMessage retuned with n error: ", response);
    }

    return response;
}