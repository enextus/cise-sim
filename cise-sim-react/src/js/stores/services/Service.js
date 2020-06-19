export default class Service {

  serviceParticipantId;
  serviceTransportMode
  endpointUrl;
  appVersion;
  messageHistoryMaxLength;
  hideIncident;

  constructor(serviceParticipantId, serviceTransportMode, endpointUrl, appVersion, messageHistoryMaxLength, hideIncident) {
    this.serviceParticipantId = serviceParticipantId;
    this.serviceTransportMode = serviceTransportMode;
    this.endpointUrl = endpointUrl;
    this.appVersion = appVersion;
    this.messageHistoryMaxLength = messageHistoryMaxLength;
    this.hideIncident = hideIncident;
  }
}
