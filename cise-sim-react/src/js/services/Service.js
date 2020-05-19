export default class Service {

  serviceParticipantId;
  serviceTransportMode
  endpointUrl;
  appVersion;
  messageHistoryMaxLength;

  constructor(serviceParticipantId, serviceTransportMode, endpointUrl, appVersion, messageHistoryMaxLength) {
    this.serviceParticipantId = serviceParticipantId;
    this.serviceTransportMode = serviceTransportMode;
    this.endpointUrl = endpointUrl;
    this.appVersion = appVersion;
    this.messageHistoryMaxLength = messageHistoryMaxLength;
  }
}
