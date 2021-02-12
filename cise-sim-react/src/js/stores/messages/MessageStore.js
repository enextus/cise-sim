/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

import {action, computed, observable} from 'mobx';
import {
    pullMessage,
    pullMessageByHistoryId,
    pullMessageByHistoryIdFull,
    pullMessageHistoryAfter,
    sendMessage
} from './MessageService';
import Message from './Message';
import MessageEasy from "./MessageEasy";
import MessageThInfo from "./MessageThInfo";

export default class MessageStore {

    @observable sentMessage          = new Message({body: "", acknowledge: ""});
    @observable receivedMessage      = new Message({body: "", acknowledge: ""});
    @observable receivedMessageError = null;

    @observable historyMsgList       = [];
    historyLasTimestamp = 0;
    threadMaxCapacity = 0;

    @observable threadMessageDetails = [];
    @observable threadWithBody = [];
    @observable threadIdSelected = "";

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


    setThreadMaxCapacity(maxLength) {
        if (this.threadMaxCapacity !== maxLength) {
         
            this.threadMaxCapacity = maxLength;
            this.historyLasTimestamp = 0

            console.log("setThreadMaxCapacity to "+maxLength)
        }
    }


    @action
    updateHistory(newChunkMsgShortInfoRcv) {

        // Adding new data to old ones and find the most recent timestamp
        const newList = [...newChunkMsgShortInfoRcv, ...this.historyMsgList];

        // Do the ordering by timestamp
        newList.sort(function(a,b) {return b.dateTime-a.dateTime});

        this.historyLasTimestamp = newList[0].dateTime;

        // take only the first threadMaxCapacity item
        this.historyMsgList = this.findMaxCorrId(newList);

    }

    findMaxCorrId(msgList) {

        let arrayOfCorrId = []

        return msgList.filter(msg => {
            
            if (arrayOfCorrId.includes(msg.correlationId)) {
                return true;
            }

            if (arrayOfCorrId.length < this.threadMaxCapacity) {
                arrayOfCorrId.push(msg.correlationId);
                return true;
            }
            return false;
        })
    }

    @action
    clearHistory() {
        this.historyMsgList = [];
    }

    @action
    updateThreadDetails(newMessagesThread) {
        this.threadMessageDetails = newMessagesThread;
    }

    @action
    updateThreadWithBody(newThreadWithBody) {
        this.threadWithBody = newThreadWithBody;
    }

    @action
    updateThreadIdSelected(correlationId) {
        this.threadIdSelected = correlationId;
    }

    @observable threadFilter=null;

    @action
    updateThreadFilter(newFilter) {
        this.threadFilter = newFilter;
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

    startPullHistoryProgressive() {
        this.interval = setInterval(async function (that)
        {
            const pullMessageResponse = await pullMessageHistoryAfter(that.historyLasTimestamp);
            if (!pullMessageResponse) return;
            if (pullMessageResponse.errorCode) {
                that.receivedMessageError = pullMessageResponse;
            } else {
                console.log("startPullHistoryProgressive received "+pullMessageResponse.length);
                that.updateHistory(pullMessageResponse);
            }
        }, 3000, this);
    }

    async getByShortInfoId(shortInfoId) {
        const messageResponse = await pullMessageByHistoryId(shortInfoId);
        if (!messageResponse) return;
        if (messageResponse.errorCode) {
            this.receivedMessageError = messageResponse;
        } else {
            this.receivedMessage = new MessageEasy(messageResponse);
        }
    }

    async getThreadWithBodyOLD(newMessagesThread) {

        let result = [];

        let msgInfo;
        for (msgInfo of newMessagesThread) {
            const body = await pullMessageByHistoryId(msgInfo.id);
            if (!body || body.errorCode) {
                result.push(new MessageThInfo(msgInfo, "Message body not available"));
            }else {
                result.push(new MessageThInfo(msgInfo, body));
            }
        }

        this.updateThreadWithBody(result);
    }

    async getThreadWithBody(newMessagesThread) {

        let result = [];
        let requestPromises = [];

        let msgInfo;
        for (msgInfo of newMessagesThread) {
            const req = pullMessageByHistoryIdFull(msgInfo);
            requestPromises.push(req);
        }

        const requestResult = await Promise.all(requestPromises);
        let response;
        for (response of requestResult) {
            result.push(response)
        }

        this.updateThreadWithBody(result);
    }
}
