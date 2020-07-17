import {http_get} from '../../api/API';


export async function getServiceSelf() {
    
    const getServiceSelfResponse = await http_get("service/self");

    console.log("getServiceSelfResponse: ", getServiceSelfResponse);

    if (!getServiceSelfResponse)
        return;

    return getServiceSelfResponse;
}