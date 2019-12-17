package eu.cise.emulator.SynchronousAcknowledgement;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.ServiceBuilder.newService;

import eu.cise.emulator.exceptions.NullClockEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.eucise.helpers.AckBuilder;
import java.sql.Date;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronousAcknowledgementFactory {

    private static final Logger LOGGER = LoggerFactory
        .getLogger(SynchronousAcknowledgementFactory.class.getName());
    private final Clock clock;
    private final AtomicLong uniqueErrorId = new AtomicLong(5000000L);


    public SynchronousAcknowledgementFactory() {
        this(Clock.systemUTC());
    }

    public SynchronousAcknowledgementFactory(Clock clock) {
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
        SynchronousAcknowledgementType syncAcknowledgmentEvent, String extraMessage) {

        AckBuilder ackBuilder = newAck()
            .id(message.getMessageID() + "_" + UUID.randomUUID().toString())
            .correlationId(computeCorrelationId(message.getCorrelationID(), message.getMessageID()))
            .creationDateTime(Date.from(clock.instant()))
            .priority(PriorityType.HIGH)
            .isRequiresAck(false);

        long ueid = 0L;

        if (syncAcknowledgmentEvent != SynchronousAcknowledgementType.SUCCESS) {
            ueid = uniqueErrorId.getAndIncrement();
            LOGGER.error("UEID:" + uniqueErrorId + " error occur detail:" + extraMessage);
        }

        switch (syncAcknowledgmentEvent) {
            case SUCCESS:
                ackBuilder.ackCode(AcknowledgementType.SUCCESS)
                    .ackDetail(StringUtils.trim("Message delivered " + extraMessage));

                /* Special case of Push Subscribe pattern*/
                if ((message instanceof Push)) {
                    Push pushMessage = (Push) message;
                    if (message.getSender().getServiceOperation()
                        == ServiceOperationType.SUBSCRIBE) {
                        if ((pushMessage.getRecipient() == null
                            && pushMessage.getDiscoveryProfiles().size() == 0) ||
                            (pushMessage.getRecipient() == null
                                && pushMessage.getDiscoveryProfiles().size() > 0)
                        ) {
                            List<Service> services = new ArrayList<>();
                            Service service = newService()
                                .id("cx.cisesim-nodecx.vessel.subscribe.consumer").build();
                            service.setParticipant(null);
                            services.add(service);
                            ackBuilder.addAllDiscoveredServices(services);
                            ackBuilder.ackDetail("Message delivered to all 1 recipients");
                        }

                        if (pushMessage.getRecipient() != null
                            && pushMessage.getDiscoveryProfiles().size() > 0) {
                            ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                                .ackDetail(buildAckDetail(extraMessage, "COM-SVC-ERR_007", ueid));
                        }
                    } else {
                        if (pushMessage.getRecipient() == null
                            && pushMessage.getDiscoveryProfiles().size() == 0) {
                            ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                                .ackDetail(buildAckDetail(extraMessage, "COM-SVC-ERR_005", ueid));
                        }
                    }
                }

                break;
            case INVALID_SIGNATURE:
                ackBuilder.recipient(newService().id("" + message.getSender().getServiceID()))
                    .sender(newService().id("" + message.getRecipient().getServiceID()))
                    .ackCode(AcknowledgementType.AUTHENTICATION_ERROR)
                    .ackDetail("Message signature not validated: Signature failed core validation.["
                        + extraMessage.substring(0, Integer.min(extraMessage.length(), 200))
                        + "...] logged as event UEID:" + ueid);
                break;
            case XML_MALFORMED:
            case INTERNAL_ERROR:
            case SEMANTIC:
                ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                    .ackDetail(buildAckDetail(extraMessage, "COM-SVC-ERR_001", ueid));
                break;
        }

        Acknowledgement acknowledgement = ackBuilder.build();
        acknowledgement.setPayload(null);
        return acknowledgement;
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
