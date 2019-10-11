import {action, computed, observable} from "mobx";
import {pullMessage, sendMessage} from "./MessageService";
import Message from "./Message";
import Error from "../errors/Error";

export default class MessageStore {
    @observable sentMessage = new Message({body: "", acknowledge: ""});
    @observable receivedMessage = new Message({body: "", acknowledge: ""});
    @observable receivedMessageError = null;
    count = 0;

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

    @action
    consumeErrorMessage() {
        this.receivedMessageError = null;
    }

    async send(seletedTemplate, messageId, correlationId, requiresAck) {
        const sendMessageResponse = await sendMessage(
            seletedTemplate,
            messageId,
            correlationId,
            requiresAck);
        console.log("sendMessageResponse:", sendMessageResponse);

        if (sendMessageResponse.errorCode) {
            console.log("Send returned an error.");
        } else {
            console.log("Send returned successfully.");
            this.sentMessage = sendMessageResponse;
        }
        return sendMessageResponse;
    }


    startPull() {
        this.interval = setInterval(async function (that)
         {
             if (!that.receivedMessageError){
                 that.receivedMessageError = new Error(500,"");
             }
                 that.receivedMessageError.errorCode = that.count;
             that.count ++;
            const pullMessageResponse = await pullMessage();
            if (!pullMessageResponse) return;
            if (pullMessageResponse.errorCode) {
                console.log("Pull returned an error.");
 //               this.receivedMessageError = pullMessageResponse;
            } else {
                console.log("Pull returned successfully.");
                that.receivedMessage = pullMessageResponse;
            }
            console.log("pullMessageResponse:", pullMessageResponse);
        }, 6000, this);
    }

}
