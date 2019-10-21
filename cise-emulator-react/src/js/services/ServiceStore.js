import {action, observable} from "mobx";
import {getServiceSelf} from "./ServiceService";
import Service from "./Service";

export default class ServiceStore {
    @observable serviceSelf = new Service("#before#loading#");


    @action
    setServiceSelf(serviceSelf) {
        this.serviceSelf = serviceSelf;
    }

    @action
    consumeErrorMessage() {
        this.receivedMessageError = null;
    }

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
