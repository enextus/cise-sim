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

package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.service.ServiceType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

public class MessageShortInfoDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageShortInfoDto.class);

    private final String id;
    private final long dateTime;
    private final String messageType;
    private final String serviceType;
    private final boolean isSent;

    // Message Thread Infos
    private final String messageId;
    private final String correlationId;

    // Sender and receiver
    private final String from;
    private final String to;

    // If Ack Synch message,
    private final String ackResult;

    private MessageShortInfoDto(String id, long dateTime, String messageType, String serviceType, boolean isSent, String messageId, String correlationId, String from, String to, String ackType) {
        this.id = id;
        this.dateTime = dateTime;
        this.messageType = messageType;
        this.serviceType = serviceType;
        this.isSent = isSent;
        this.messageId = messageId;
        this.correlationId = correlationId;
        this.from = from != null ? from : "";
        this.to = to != null ? to : "";
        this.ackResult = ackType;
    }

    public static MessageShortInfoDto getInstance(Message ciseMessage, boolean isSent, Date timestamp, String uuid) throws IllegalArgumentException {

        long  dateTime = timestamp.getTime();

        MessageTypeEnum messageType = MessageTypeEnum.valueOf(ciseMessage);
        String messageTypeName = messageType.getUiName();

        String  serviceType = "";
        if (messageType != MessageTypeEnum.ACK_ASYNC && messageType != MessageTypeEnum.ACK_SYNC) {
            if (ciseMessage.getSender() == null) {
                LOGGER.warn("In message id [{}] of type [{}] no sender was found", ciseMessage.getMessageID(), messageType.getUiName());
            } else {
                ServiceType serviceTypeEnum = ciseMessage.getSender().getServiceType();
                if (serviceTypeEnum == null) {
                    LOGGER.warn("In message id [{}] of type [{}] no service type was found", ciseMessage.getMessageID(), messageType.getUiName());
                } else {
                    serviceType = serviceTypeEnum.value();
                }
            }
        }

        String ackResult = "";
        if (messageType == MessageTypeEnum.ACK_SYNC) {
            AcknowledgementType ackType = ((Acknowledgement) ciseMessage).getAckCode();
            ackResult = ackType.value();
        }

        // Sender & Recipient
        String from = ciseMessage.getSender() != null ? ciseMessage.getSender().getServiceID() : "";
        String to = ciseMessage.getRecipient() != null ? ciseMessage.getRecipient().getServiceID() : "";


        MessageShortInfoDto instance = new MessageShortInfoDto(uuid, dateTime, messageTypeName, serviceType, isSent, ciseMessage.getMessageID(), ciseMessage.getCorrelationID(), from, to, ackResult);

        check(instance);

        return instance;
    }

    private static void check(MessageShortInfoDto instance) throws IllegalArgumentException {

        if (StringUtils.isEmpty(instance.getId())) {
            throw new IllegalArgumentException("MessageID is empty");
        }

        if (StringUtils.isEmpty(instance.getMessageType())) {
            throw new IllegalArgumentException("Type of Message is empty");
        }
    }

    public String getId() {
        return id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public boolean isSent() {
        return isSent;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAckResult() {
        return ackResult;
    }
}
