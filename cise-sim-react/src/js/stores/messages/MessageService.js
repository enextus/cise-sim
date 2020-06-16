import {http_delete, http_get, http_post} from '../../api/API'
import Message from './Message';
import MessageShortInfo from "./MessageShortInfo";
import MessageThInfo from "./MessageThInfo";
import LabelsIncidentDto from "../../components/IncidentForm/dto/LabelsIncidentDto";

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
        console.log("sendMessage retuned with n error: ", sendMessagePostResponse);
        return sendMessagePostResponse;
    }

    return new Message(sendMessagePostResponse);
}


export async function pullMessage() {
    
    const pullMessageDeleteResponse = await http_delete("messages/latest");
    if (!pullMessageDeleteResponse) return;

    if (pullMessageDeleteResponse.errorCode) {
        console.log("pullMessage returned with n error: ", pullMessageDeleteResponse);
        return pullMessageDeleteResponse;
    }

    return new Message(pullMessageDeleteResponse);
}

export async function pullMessageHistoryAfter(timestamp) {

    const pullHistoryMessageResponse = await http_get("history/latest/"+timestamp);
    if (!pullHistoryMessageResponse) return;

    if (pullHistoryMessageResponse.errorCode) {
        console.log("pullMessageHistoryAfter returned with n error: ", pullHistoryMessageResponse);
        return pullHistoryMessageResponse;
    }

    return  pullHistoryMessageResponse.map(m => new MessageShortInfo(m));
}

export async function pullMessageByHistoryId(id) {
    const messageResponse = await http_get("history/message/"+id);
    if (!messageResponse) return;

    if (messageResponse.errorCode) {
        console.log("pullMessageByHistoryId returned with n error: ", messageResponse);
        return messageResponse;
    }

    return  messageResponse;
}

export async function pullMessageByHistoryIdFull(msgInfo) {
    const messageResponse = await http_get("history/message/"+msgInfo.id);
    if (!messageResponse) return;

    if (messageResponse.errorCode) {
        console.log("pullMessageByHistoryIdFull returned with n error: ", messageResponse);
        return new MessageThInfo(msgInfo, "Message body not available");
    }

    return  new MessageThInfo(msgInfo, messageResponse);
}


export async function getLabelsIncident() {

    const labelsResponse = await http_get("messages/labels/incident");
    if (!labelsResponse) return;

    if (labelsResponse.errorCode) {
        console.log("pullMessage returned with n error: ", labelsResponse);
        return labelsResponse;
    }

    return new LabelsIncidentDto(labelsResponse);
}
