import {action, observable} from "mobx";

export default class MessageModel {
  id = Math.random();
  @observable messageId = "";
  @observable correlationId;
  @observable source;
  @observable destination;
  @observable messageType;
  @observable templateService;
  @observable templatePayload;
  @observable asyncAcknowledge = false;
  @observable finished = false;
  @observable steps = [];
  @observable content = "";
  @observable status = "";

  constructor(messageUid, correlationId, messageType, templateService,
      templatePayload, asyncAcknowledge, source, destination) {
    this.messageId = messageUid;
    this.correlationId = correlationId;
    this.messageType = messageType;
    this.templateService = templateService;
    this.templatePayload = templatePayload;
    this.asyncAcknowledge = asyncAcknowledge;
    this.source = source;
    this.destination = destination;
  }

  @action
  setCorrelationId(correlationId) {
    // should use actual instance templateService and templatePayload
    this.correlationId = correlationId;
    this.obtainPreview();
  }

  @action
  setTemplateService(templateService) {
    // should use actual instance templateService and templatePayload
    this.templateService = templateService;
    this.obtainPreview();
  }

  @action
  setTemplatePayload(templatePayload) {
    // should use actual instance templateService and templatePayload
    this.templatePayload = templatePayload;
    this.obtainPreview();
  }

  @action
  setAsyncAcknowledge(asyncAcknowledge) {

  }

}

