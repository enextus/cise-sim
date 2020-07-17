import {action, decorate, observable} from 'mobx';
import {getServiceSelf} from './ServiceService';
import Service from './Service';

export default class ServiceStore {

  serviceSelf = new Service(
      "...",
      "...",
      "...",
      "...",
      0,
      true);

  async loadServiceSelf() {
    const simInfo = await getServiceSelf();

    if (simInfo.errorCode) {
      console.log("getServiceSelf returned an error.", simInfo.errorCode);
    } else {

      this.serviceSelf = new Service(
          simInfo.serviceParticipantId,
          simInfo.serviceTransportMode,
          simInfo.endpointUrl,
          simInfo.appVersion,
          simInfo.messageHistoryMaxLength,
          simInfo.hideIncident);

      console.log("getServiceSelf returned successfully.",
          this.serviceSelf.serviceParticipantId, " - with mode  - ",
          this.serviceSelf.serviceTransportMode);
    }
  }

}

decorate(ServiceStore, {
  serviceSelf: observable,
  setServiceSelf: action,
  consumeErrorMessage: action,
  loadServiceSelf: action
})
