import {http_get} from '../api/API'
import Service from "./Service";


export async function getServiceSelf() {
    
    const getServiceSelfResponse = await http_get("service/self");
    console.log("getServiceSelfResponse: ", getServiceSelfResponse);
    if (!getServiceSelfResponse) return;

    if (getServiceSelfResponse.serviceParticipantId) {
        console.log("pullMessagePostResponse returned with n error: ", getServiceSelfResponse);
        return getServiceSelfResponse;
    }
    console.log("getServiceSelfResponse: ", getServiceSelfResponse);
    return new Service(getServiceSelfResponse);
}