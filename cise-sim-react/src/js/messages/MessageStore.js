import {action, computed, observable} from 'mobx';
import {pullMessage, pullMessageHistory, sendMessage} from './MessageService';
import Message from './Message';


export default class MessageStore {
    @observable sentMessage          = new Message({body: "", acknowledge: ""});
    @observable receivedMessage      = new Message({body: "", acknowledge: ""});
    @observable receivedMessageError = null;

    @observable historyMsgList       = [];

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

    // In ingresso abbiamo una lista di MessageShortInfo
    updateHistory(msgShortList) {
        const newList = [...this.historyMsgList];
        msgShortList.forEach(t => newList.push(t));
        this.historyMsgList = newList;
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
            const pullMessageResponse = await pullMessage();
            if (!pullMessageResponse) return;
            if (pullMessageResponse.errorCode) {
                that.receivedMessageError = pullMessageResponse;
            } else {
                that.receivedMessage = pullMessageResponse;
            }
        }, 3000, this);
    }

    startPullHistory() {
        this.interval = setInterval(async function (that)
        {
            const pullMessageResponse = await pullMessageHistory();
            if (!pullMessageResponse) return;
            if (pullMessageResponse.errorCode) {
                that.receivedMessageError = pullMessageResponse;
            } else {
                that.updateHistory(pullMessageResponse);
            }
        }, 3000, this);
    }
}
