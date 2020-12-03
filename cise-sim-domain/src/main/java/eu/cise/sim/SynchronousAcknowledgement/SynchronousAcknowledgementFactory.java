package eu.cise.sim.SynchronousAcknowledgement;

import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.sim.exceptions.NullClockEx;
import eu.cise.sim.utils.MockMessage;
import eu.eucise.helpers.AckBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Date;
import java.time.Clock;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.sim.helpers.Asserts.notNull;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.ServiceBuilder.newService;

public class SynchronousAcknowledgementFactory {

    private static final int MAX_MESSAGE_LEN = 200;
    private static final String COMMON_SERVICES_ERROR_1 = "COM-SVC-ERR_001";
    private static final String COMMON_SERVICES_ERROR_5 = "COM-SVC-ERR_005";
    private static final String COMMON_SERVICES_ERROR_7 = "COM-SVC-ERR_007";

    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousAcknowledgementFactory.class.getName());
    private final Clock clock;
    private final AtomicLong uniqueErrorId = new AtomicLong(5000000L);

    public SynchronousAcknowledgementFactory() {
        this(Clock.systemUTC());
    }

    public SynchronousAcknowledgementFactory(Clock clock) {
        this.clock = notNull(clock, NullClockEx.class);
    }

    public Acknowledgement buildAck(Message message,
        SynchronousAcknowledgementType syncAcknowledgmentEvent, String statusDetails) {

        String statusMessage = truncate(statusDetails);
        AckBuilder ackBuilder = buildDefaultAck(message, statusMessage);

        switch (syncAcknowledgmentEvent) {
            case SUCCESS:
                if (message instanceof PullRequest) {
                    buildAckForPull(ackBuilder, (PullRequest) message);
                } else if (message instanceof Push) {
                    buildAckForPush(ackBuilder, (Push) message, statusMessage);
                }
                break;
            case INVALID_SIGNATURE:
                buildAckForInvalidSignature(ackBuilder, message, statusMessage);
                break;
            case XML_MALFORMED:
            case INTERNAL_ERROR:
            case SEMANTIC:
                buildAckForOtherErrors(ackBuilder, statusMessage);
                break;
        }

        Acknowledgement acknowledgement = ackBuilder.build();
        acknowledgement.setPayload(null);
        return acknowledgement;
    }

    private AckBuilder buildDefaultAck(Message originalMessage, String statusMessage){
        return newAck()
                .id(originalMessage.getMessageID() + "_" + UUID.randomUUID().toString())
                .correlationId(computeCorrelationId(originalMessage.getCorrelationID(), originalMessage.getMessageID()))
                .creationDateTime(Date.from(clock.instant()))
                .priority(PriorityType.HIGH)
                .isRequiresAck(false)
                .ackCode(AcknowledgementType.SUCCESS)
                .ackDetail("Message delivered " + statusMessage);
    }

    private String truncate(String text) {
        return text.substring(0, Integer.min(text.length(), MAX_MESSAGE_LEN)).trim();
    }

    private long errorCode(){
        return uniqueErrorId.getAndIncrement();
    }

    private AckBuilder buildAckForPull(AckBuilder ackBuilder, PullRequest message)  {
        try {
            if (isPullDiscovery(message))
                ackBuilder.addAllDiscoveredServices(
                        MockMessage.getDiscoveryAckSynch().getDiscoveredServices());
        } catch (IOException e) {
            LOGGER.warn("Adding discovered services throw exception {}", e.getMessage());
        }
        return ackBuilder;
    }

    private boolean isPullDiscovery(PullRequest message) {
        return message.getPullType() == PullType.DISCOVER;
    }

    private AckBuilder buildAckForPush(AckBuilder ackBuilder, Push pushMessage, String statusMessage) {
        if (isPushSubscribe(pushMessage)) {
            if (pushMessage.getRecipient() == null)
                ackBuilder.addAllDiscoveredServices(Arrays.asList(newVesselSubscribeConsumer()))
                    .ackDetail("Message delivered to all 1 recipients");
            else if (pushMessage.getDiscoveryProfiles().size() != 0)
                ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                        .ackDetail(buildErrorDetail(statusMessage, COMMON_SERVICES_ERROR_7));

        } else if (isPushToNoOne(pushMessage)) {
            ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                    .ackDetail(buildErrorDetail(statusMessage, COMMON_SERVICES_ERROR_5));
        }
        return ackBuilder;
    }

    private boolean isPushSubscribe(Push message) {
        return message.getSender().getServiceOperation() == ServiceOperationType.SUBSCRIBE;
    }

    private boolean isPushToNoOne(Push pushMessage) {
        return pushMessage.getRecipient() == null
                && pushMessage.getDiscoveryProfiles().size() == 0;
    }

    private final static String VESSEL_SUBS_CONS_SERVICEID = "cx.cisesim-nodecx.vessel.subscribe.consumer";
    private Service newVesselSubscribeConsumer() {
        Service service = newService()
                .id(VESSEL_SUBS_CONS_SERVICEID).build();
        service.setParticipant(null);
        return service;
    }

    private AckBuilder buildAckForInvalidSignature(AckBuilder ackBuilder, Message message, String statusMessage) {
       return ackBuilder.recipient(newService().id(message.getSender().getServiceID()))
                .sender(newService().id(message.getRecipient().getServiceID()))
                .ackCode(AcknowledgementType.AUTHENTICATION_ERROR)
                .ackDetail(buildSignatureErrorDetail(statusMessage));
    }

    private AckBuilder buildAckForOtherErrors(AckBuilder ackBuilder, String statusMessage) {
        return ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                .ackDetail(buildErrorDetail(statusMessage, COMMON_SERVICES_ERROR_1));
    }

    private String buildSignatureErrorDetail(String errorMessage) {
        long internalErrorCode = errorCode();
        LOGGER.error("UEID:" + internalErrorCode + " error occur detail:" + errorMessage);
        return "Message signature not validated: Signature failed core validation.["
                + errorMessage
                + "...] logged as event UEID:" + internalErrorCode;
    }

    private String buildErrorDetail(String errorMessage, String errorCode) {
        long internalErrorCode = errorCode();
        LOGGER.error("UEID:" + internalErrorCode + " error occur detail:" + errorMessage);
        return "Validation error code: " + errorCode + " \n" +
            " Validation error message: [" + errorMessage + "...] logged as event UEID:"
            + internalErrorCode;
    }

    private String computeCorrelationId(String correlationId, String messageId) {
        if (isNullOrEmpty(correlationId)) {
            return messageId;
        }
        return correlationId;
    }

}
