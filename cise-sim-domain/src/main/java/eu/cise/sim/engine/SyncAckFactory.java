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

package eu.cise.sim.engine;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.sim.exceptions.NullClockEx;
import eu.cise.sim.utils.MockMessage;
import eu.eucise.helpers.AckBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Date;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.servicemodel.v1.message.AcknowledgementType.*;
import static eu.cise.servicemodel.v1.message.PriorityType.HIGH;
import static eu.cise.servicemodel.v1.message.PullType.DISCOVER;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.SUBSCRIBE;
import static eu.cise.sim.helpers.Asserts.notNull;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.abbreviate;

public class SyncAckFactory {

    private final Clock clock;
    private final AtomicLong uniqueErrorId = new AtomicLong(5000000L);
    private final Logger logger = LoggerFactory.getLogger(SyncAckFactory.class);


    public SyncAckFactory() {
        this(Clock.systemUTC());
    }

    public SyncAckFactory(Clock clock) {
        this.clock = notNull(clock, NullClockEx.class);
    }

    /**
     * TODO understand better if this is needed or not the emuConfig.serviceType() could be null in
     * the case we don't want to override the message template service type. We can both: 1) change
     * this behaviour and decide that emuConfig.serviceType() is compulsory; 2) accept any kind of
     * message coming from the outside whatever service type they have.
     *
     * @param message the message the acknowledgment should be built on.
     * @return an ack to be sent to the client
     */
    public Acknowledgement buildAck(Message message,
                                    SyncAckType syncAckEvent,
                                    String exceptionMessage) {

        AckBuilder ackBuilder = newAck()
            .id(message.getMessageID() + "_" + UUID.randomUUID().toString())
            .correlationId(computeCorrelationId(message.getCorrelationID(), message.getMessageID()))
            .creationDateTime(Date.from(clock.instant()))
            .priority(HIGH)
            .isRequiresAck(false);

        switch (syncAckEvent) {
            case SUCCESS:
                successAck(message, exceptionMessage, ackBuilder);
                break;
            case INVALID_SIGNATURE:
                invalidSignatureAck(message, exceptionMessage, ackBuilder);
                break;
            case XML_MALFORMED:
            case INTERNAL_ERROR:
            case SEMANTIC:
                ackBuilder.ackCode(BAD_REQUEST).ackDetail(buildAckDetail(exceptionMessage, "COM-SVC-ERR_001", uniqueErrorId.get()));
                break;
        }

        Acknowledgement acknowledgement = ackBuilder.build();
        acknowledgement.setPayload(null);

        // Mock discovery services
        if (message instanceof PullRequest) {
            PullRequest msgPull = (PullRequest) message;
            if (msgPull.getPullType() == DISCOVER) {

                try {
                    Acknowledgement mockDiscovery = MockMessage.getDiscoveryAckSynch();
                    acknowledgement.getDiscoveredServices().addAll(mockDiscovery.getDiscoveredServices());
                } catch (IOException e) {
                    logger.warn("Adding discovered services throw exception {}", e.getMessage());
                }
            }
        }

        return acknowledgement;
    }

    private void invalidSignatureAck(Message message, String exceptionMessage, AckBuilder ackBuilder) {
        final String detailMessage = "Message signature not validated: Signature failed core validation.[%s...] logged as event UEID:%d";
        ackBuilder
            .recipient(
                newService().id(message.getSender().getServiceID()))
            .sender(
                newService().id(message.getRecipient().getServiceID()))
            .ackCode(AUTHENTICATION_ERROR)
            .ackDetail(format(detailMessage, abbreviate(exceptionMessage, 200), uniqueErrorId.get()));
    }

    private void successAck(Message message, String exceptionMessage, AckBuilder ackBuilder) {
        ackBuilder.ackCode(SUCCESS).ackDetail("Message delivered");

        /* Special case of Push Subscribe pattern*/
        if ((message instanceof Push)) {
            Push pushMessage = (Push) message;

            if (isPushSubscribeType(message)) {
                // Double check. It does not seems correct: the discovery message
                // should be answered for all the push / pullrequest unknown
                // not for the PushSubscribe
                if (pushMessage.getRecipient() == null) {
                    List<Service> services = new ArrayList<>();
                    Service service = newService().id("cx.cisesim-nodecx.vessel.subscribe.consumer").build();
                    service.setParticipant(null);
                    services.add(service);
                    ackBuilder.addAllDiscoveredServices(services);
                    ackBuilder.ackDetail("Message delivered to all 1 recipients");
                }

                if (areRecipientAndDiscoveryValued(pushMessage)) {
                    ackBuilder.ackCode(BAD_REQUEST)
                        .ackDetail(buildAckDetail(exceptionMessage, "COM-SVC-ERR_007", uniqueErrorId.get()));
                }

                return;
            }

            if (recipientAndDiscoveryAreEmpty(pushMessage)) {
                ackBuilder.ackCode(BAD_REQUEST)
                    .ackDetail(buildAckDetail(exceptionMessage, "COM-SVC-ERR_005", uniqueErrorId.get()));
            }
        }
    }

    private boolean areRecipientAndDiscoveryValued(Push pushMessage) {
        return pushMessage.getRecipient() != null && !pushMessage.getDiscoveryProfiles().isEmpty();
    }

    private boolean recipientAndDiscoveryAreEmpty(Push pushMessage) {
        return pushMessage.getRecipient() == null && pushMessage.getDiscoveryProfiles().isEmpty();
    }

    private boolean isPushSubscribeType(Message message) {
        return message.getSender().getServiceOperation() == SUBSCRIBE;
    }

    private String buildAckDetail(String extraMessage, String errorCode, long ueid) {
        return "Validation error code: " + errorCode + " \n" +
            " Validation error message: [" + extraMessage
            .substring(0, Integer.min(extraMessage.length(), 200)) + "...] logged as event UEID:"
            + ueid;
    }

    private String computeCorrelationId(String correlationId, String messageId) {
        if (isNullOrEmpty(correlationId)) {
            return messageId;
        }
        return correlationId;
    }

}
