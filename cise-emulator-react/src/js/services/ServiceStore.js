import {action, observable,decorate} from "mobx";
import {getServiceSelf} from "./ServiceService";
import Service from "./Service";

export default class ServiceStore {
 serviceSelf = new Service("#before#loading#");

    async loadServiceSelf() {
        const getServiceSelfResponse = await getServiceSelf();
        console.log("getServiceSelf:", getServiceSelfResponse);

        if (getServiceSelfResponse.errorCode) {
            console.log("getServiceSelf returned an error.");
        } else {
            this.serviceSelf = new Service(getServiceSelfResponse.serviceParticipantId);
            console.log("getServiceSelf returned successfully.",this.serviceSelf.serviceParticipantId);
        }
    }

}

decorate(ServiceStore, {
    serviceSelf: observable,
    setServiceSelf: action,
    consumeErrorMessage:action,
    loadServiceSelf:action
})
