export default class Service {

  serviceParticipantId;
  serviceTransportMode
  endpointUrl;
  appVersion;
  messageHistoryMaxLength;
  showIncident;

  constructor(serviceParticipantId, serviceTransportMode, endpointUrl, appVersion, messageHistoryMaxLength, showIncident) {
    this.serviceParticipantId = serviceParticipantId;
    this.serviceTransportMode = serviceTransportMode;
    this.endpointUrl = endpointUrl;
    this.appVersion = appVersion;
    this.messageHistoryMaxLength = messageHistoryMaxLength;
    this.showIncident = showIncident;
  }
}
