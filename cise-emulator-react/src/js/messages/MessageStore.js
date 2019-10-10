import { observable, computed, action } from "mobx";
import {sendMessage} from "./MessageService";
import Message from "./Message";

export default class MessageStore {
    @observable sentMessage = new Message({body: "", acknowledge: ""});
    @observable receivedMessage;

    @computed
    get isSentMessagePresent() {
        return !(this.sentMessage);
    }

    @computed
    get isReceivedMessagePresent() {
        return !(this.sentMessage);
    }

    @action
    setSentMessage(sentMessage) {
        this.sentMessage = sentMessage;
    }

    @action
    setReceivedMessage(receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    async send(seletedTemplate) {
        const sendMessageResponse = await sendMessage(
            seletedTemplate,
            this.messageId,
            this.correlationId,
            this.requiresAck);
        console.log("sendMessageResponse:", sendMessageResponse);

        if (sendMessageResponse.errorCode) {
            console.log("TemplateStore preview returned an error.");
        } else {
            console.log("TemplateStore preview returned successfully.");
            this.sentMessage = sendMessageResponse;
        }
        return getTemplateByIdResposnse;
    }

}
