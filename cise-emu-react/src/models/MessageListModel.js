import { observable, computed, action } from "mobx";

import MessageModel from "./MessageModel";
import MessageType from "./MessageType";

export default class MessageListModel {
  @observable messages = [];

  @computed
  get uncompletedMessageFlow() {
    return this.messages.filter(message => !message.finished).length;
  }
  @computed
  get syncMessageInFlow() {
    return this.messages.filter(message => !message.asyncAcknowledge).length;
  }
  @computed
  get asyncMessageInFlow() {
    return this.messages.filter(message => message.asyncAcknowledge).length;
  }

  @action
  addMessage(correlationId) {
    this.messages.push(new MessageModel(correlationId,MessageType.MASTER_OUT,"templatePush.xml","you.xml",true,"#TOBELOADED#","OTHER"));
  }

  @action
  addMessagefull (correlationId, MessageType, templateService, templatePayload, asyncAcknowledge,source,destination) {
    this.messages.push(new MessageModel(correlationId,MessageType.MASTER_OUT,templateService,templatePayload,asyncAcknowledge,source,destination));
  }
}

