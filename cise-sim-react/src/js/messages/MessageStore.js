import {action, computed, observable} from 'mobx';
import {pullMessage, pullMessageHistory, pullMessageHistoryAfter, sendMessage} from './MessageService';
import Message from './Message';
import Config from 'Config';

export default class MessageStore {
    @observable sentMessage          = new Message({body: "", acknowledge: ""});
    @observable receivedMessage      = new Message({body: "", acknowledge: ""});
    @observable receivedMessageError = null;

    @observable historyMsgList       = [];
    historyLasTimestamp = 0;
    historyMaxCapacity = Config.max_history_msg;

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

    /*
    @action
    updateHistorySecure(newChunkMsgShortInfoRcv) {

        // Adding new data to old ones and find the most recent timestamp
        const newList = [...this.historyMsgList];

        newChunkMsgShortInfoRcv.forEach(t => {
            newList.push(t);
            if (t.dateTime > this.historyLasTimestamp) this.historyLasTimestamp = t.dateTime;
        });

        // Do the ordering by timestamp
        newList.sort(function(a,b) {return b.dateTime-a.dateTime});

        // take only the first historyMaxCapacity item
        this.historyMsgList = newList.slice(0, this.historyMaxCapacity);
    }
    */

    @action
    updateHistory(newChunkMsgShortInfoRcv) {

        const newList = [...newChunkMsgShortInfoRcv, ...this.historyMsgList];
        this.historyLasTimestamp = newList[0].dateTime;
        this.historyMsgList = newList.slice(0, this.historyMaxCapacity);
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

    startPullHistoryProgressive() {
        this.interval = setInterval(async function (that)
        {
            const pullMessageResponse = await pullMessageHistoryAfter(that.historyLasTimestamp);
            if (!pullMessageResponse) return;
            if (pullMessageResponse.errorCode) {
                that.receivedMessageError = pullMessageResponse;
            } else {
                that.updateHistory(pullMessageResponse);
            }
        }, 3000, this);
    }
}
