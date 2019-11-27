export default class Service {

  serviceParticipantId;
  serviceTransportMode
  endpointUrl;

  constructor(serviceParticipantId, serviceTransportMode, endpointUrl) {
    this.serviceParticipantId = serviceParticipantId;
    this.serviceTransportMode = serviceTransportMode;
    this.endpointUrl = endpointUrl;
  }
}
