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

package eu.cise.cli.repository;

import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;

import static eu.cise.servicemodel.v1.message.AcknowledgementType.AUTHENTICATION_ERROR;

public enum MessageTypeEnum {

    PUSH("Push", "PUSH"),
    PULL_RESPONSE("Pull Response", "PULLRESPONSE"),
    PULL_REQUEST("Pull Request", "PULLREQUEST"),
    FEEDBACK("Feedback", "FEEDBACK"),
    ACK_SYNC("Sync Ack", "SYNCACK"),
    ACK_ASYNC("Async Ack", "ASYNCACK"),
    PUBLISH("Publish", "PUBLISH"),
    SUBSCRIBE("Subscribe", "SUBSCRIBE");


    private final String uiName;
    private final String fileName;

    MessageTypeEnum(String uiName, String fileName) {
        this.uiName = uiName;
        this.fileName = fileName;
    }

    public static MessageTypeEnum valueOf(Message message) throws IllegalArgumentException {

        if (message instanceof PullRequest) {
            // Suscribe is a pull request with <ServiceOperation>Subscribe</ServiceOperation>
            return (message.getSender().getServiceOperation() == ServiceOperationType.SUBSCRIBE) ?
                    SUBSCRIBE :
                    PULL_REQUEST;
        }


        if (message instanceof PullResponse) {
            return PULL_RESPONSE;
        }

        if (message instanceof Push) {
            // Publish is Push with <ServiceOperation>Subscribe</ServiceOperation>
            return (message.getSender().getServiceOperation() == ServiceOperationType.SUBSCRIBE) ?
                    PUBLISH :
                    PUSH;
        }

        if (message instanceof Feedback) {
            return FEEDBACK;
        }

        if (message instanceof Acknowledgement) {
            Acknowledgement ack = (Acknowledgement) message;
            if (message.getSender() == null || ack.getAckCode() == AUTHENTICATION_ERROR) {
                return ACK_SYNC;
            } else {
                return ACK_ASYNC;
            }
        }

        throw new IllegalArgumentException("Message class is unknown " + message.getClass().getCanonicalName());
    }

    public String getUiName() {
        return uiName;
    }

    public String getFileName() {
        return fileName;
    }
}
