
export default class Service {

    serviceParticipantId;
    serviceTransportMode

    constructor(serviceParticipantId,serviceTransportMode) {
        this.serviceParticipantId = serviceParticipantId;
        this.serviceTransportMode = serviceTransportMode;
    }

    getParticipantId(){
        return serviceParticipantId;
    }

    getTransportMode(){
        return serviceTransportMode;
    }

}
