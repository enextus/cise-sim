//import {observable} from "mobx";

export default class Message {
    // @observable
    // this.service_SeaBassin;
    // @observable
    // this.service_Type;
    // @observable
    // this.service_Operation;
    // @observable
    // this.service_Role;
    // @observable
    // this.service_ID;
    // @observable
    // this.service_participant_ID;
    // @observable
    serviceParticipantId;

    constructor(value) {
        this.serviceParticipantId = value;
    }

    getParticipantId(){
        return serviceParticipantId;
    }

}