import { observable } from "mobx";
export default class MessageModel {
  id = Math.random();
  @observable correlationId;
  @observable source;
  @observable destination;
  @observable messageType;
  @observable templateService;
  @observable templatePayload;
  @observable asyncAcknowledge = false;
  @observable finished = false;
  @observable steps = [];

  constructor(correlationId, messageType, templateService, templatePayload,asyncAcknowledge,source,destination) {
    this.correlationId = correlationId;
    this.messageType=messageType;
    this.templateService=templateService;
    this.templatePayload=templatePayload;
    this.asyncAcknowledge=asyncAcknowledge;
    this.source=source;
    this.destination=destination;
    }
  }

