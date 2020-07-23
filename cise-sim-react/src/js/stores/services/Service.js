export default class Service {

  serviceParticipantId;
  serviceTransportMode
  endpointUrl;
  appVersion;
  messageHistoryMaxLength;
  showIncident;
  discoverySender;

  constructor(serviceParticipantId, serviceTransportMode, endpointUrl, appVersion, messageHistoryMaxLength, showIncident, discoverySender) {
    this.serviceParticipantId = serviceParticipantId;
    this.serviceTransportMode = serviceTransportMode;
    this.endpointUrl = endpointUrl;
    this.appVersion = appVersion;
    this.messageHistoryMaxLength = messageHistoryMaxLength;
    this.showIncident = showIncident;
    this.discoverySender = discoverySender;
  }
}
