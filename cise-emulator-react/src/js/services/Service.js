export default class Service {

  serviceParticipantId;
  serviceTransportMode
  endpointUrl;
  appVersion;

  constructor(serviceParticipantId, serviceTransportMode, endpointUrl, appVersion) {
    this.serviceParticipantId = serviceParticipantId;
    this.serviceTransportMode = serviceTransportMode;
    this.endpointUrl = endpointUrl;
    this.appVersion = appVersion;
  }
}
