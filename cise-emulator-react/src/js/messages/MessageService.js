import {http_delete, http_post} from '../api/API'
import Message from "./Message";

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
    console.log("pullMessage");
    const pullMessageDeleteResponse = await http_delete("messages/latest");

    if (!pullMessageDeleteResponse) return;

    if (pullMessageDeleteResponse.errorCode) {
        console.log("pullMessagePostResponse returned with n error: ", pullMessageDeleteResponse);
        return pullMessageDeleteResponse;
    }
    console.log("pullMessagePostResponse: ", pullMessageDeleteResponse);
    return new Message(pullMessageDeleteResponse);
}