import {action, observable} from "mobx";
import MessageModel from "./MessageModel";

export default class MessageStore {
    @observable messages = [];
    @observable currentMessage;

    @action
    createNewMessage(correlationId, messageType, templateService, templatePayload,
                     asyncAcknowledge, source, destination) {
        this.messages.push(
            new MessageModel(this.getId(), correlationId, messageType,
                templateService, templatePayload, asyncAcknowledge, source,
                destination));
        this.currentMessage = this.messages[0];
        console.log(this.messages.toString());
    }

    getId() {
        let d = new Date().getTime()
        const uuid = 'xxxxx-xxxxyx'.replace(/[xy]/g, function (c) {
            const r = ((d + Math.random() * 16) % 16) | 0
            d = Math.floor(d / 16)
            return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
        })
        return uuid
    }
}

