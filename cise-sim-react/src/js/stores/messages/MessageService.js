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

import {http_delete, http_get, http_post} from '../../api/API'
import Message from './Message';
import MessageShortInfo from "./MessageShortInfo";
import MessageThInfo from "./MessageThInfo";
import LabelsIncidentDto from "../../forms/IncidentForm/dto/LabelsIncidentDto";

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
