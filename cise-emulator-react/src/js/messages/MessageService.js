import {http_post} from '../api/API'
import Message from "./Message";

export async function sendMessage(templateId, messageId, correlationId, requiresAck) {
    console.log("sendMessage");
    const sendMessagePostResposnse = await http_post("templates/" + templateId,
        {
            messageId: messageId,
            correlationId: correlationId,
            requestAck: requiresAck,
        }
    );

    if(sendMessagePostResposnse.errorCode){
        // return Error object
        console.log("sendMessagePostResposnse retuned with n error: ", sendMessagePostResposnse);
        return sendMessagePostResposnse;
    }

    console.log("sendMessagePostResposnse: ", sendMessagePostResposnse);
    return new Message(sendMessagePostResposnse);
}