import {http_delete, http_get, http_post} from '../api/API'
import Message from './Message';
import MessageShortInfo from "./MessageShortInfo";

export async function sendMessage(templateId, messageId, correlationId, requiresAck) {
    console.log("sendMessage");
    const sendMessagePostResponse = await http_post("templates/" + templateId,
        {
            messageId: messageId,
            correlationId: correlationId,
            requiresAck: requiresAck,
        }
    );

    if (sendMessagePostResponse.errorCode) {
        // return Error object
        console.log("sendMessagePostResposnse retuned with n error: ", sendMessagePostResponse);
        return sendMessagePostResponse;
    }

    console.log("sendMessagePostResposnse: ", sendMessagePostResponse);
    return new Message(sendMessagePostResponse);
}


export async function pullMessage() {
    
    const pullMessageDeleteResponse = await http_delete("messages/latest");
    if (!pullMessageDeleteResponse) return;

    if (pullMessageDeleteResponse.errorCode) {
        console.log("pullMessagePostResponse returned with n error: ", pullMessageDeleteResponse);
        return pullMessageDeleteResponse;
    }

    return new Message(pullMessageDeleteResponse);
}

export async function pullMessageHistory() {

    const pullHistoryMessageResponse = await http_get("history/latest");
    if (!pullHistoryMessageResponse) return;

    if (pullHistoryMessageResponse.errorCode) {
        console.log("pullMessagePostResponse returned with n error: ", pullHistoryMessageResponse);
        return pullHistoryMessageResponse;
    }

    return  pullHistoryMessageResponse.map(m => new MessageShortInfo(m));
}

export async function pullMessageHistoryAfter(timestamp) {

    const pullHistoryMessageResponse = await http_get("history/latest/"+timestamp);
    if (!pullHistoryMessageResponse) return;

    if (pullHistoryMessageResponse.errorCode) {
        console.log("pullMessagePostResponse returned with n error: ", pullHistoryMessageResponse);
        return pullHistoryMessageResponse;
    }

    return  pullHistoryMessageResponse.map(m => new MessageShortInfo(m));
}

export async function pullMessageByHistoryId(id) {

    const messageResponse = await http_get("history/message/"+id);
    if (!messageResponse) return;

    if (messageResponse.errorCode) {
        console.log("pullMessagePostResponse returned with n error: ", messageResponse);
        return messageResponse;
    }

    return  messageResponse;
}