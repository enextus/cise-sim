package eu.cise.emulator.SynchronousAcknowledgement;

import eu.cise.emulator.exceptions.NullClockEx;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.eucise.helpers.AckBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;
import static eu.eucise.helpers.ServiceBuilder.newService;

public class SynchronousAcknowledgementFactory {
    private final Clock clock;
    private final AtomicLong uniqueErrorId = new AtomicLong(5000000L);
    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousAcknowledgementFactory.class.getName());


    public SynchronousAcknowledgementFactory() {
        this(Clock.systemUTC());
    }

    public SynchronousAcknowledgementFactory(Clock clock) {
        this.clock = notNull(clock, NullClockEx.class);
    }

    /**
     * TODO understand better if this is needed or not
     * the emuConfig.serviceType() could be null in the case we don't want to override
     * the message template service type.
     * We can both:
     * 1) change this behaviour and decide that emuConfig.serviceType() is compulsory;
     * 2) accept any kind of message coming from the outside whatever service type they have.
     *
     * @param message the message the acknowledgment should be built on.
     * @return an ack to be sent to the client
     */
    public Acknowledgement buildAck(Message message, SynchronousAcknowledgementType syncAcknowledgmentEvent, String extraMessage) {


        AckBuilder ackBuilder = newAck()
                .id(message.getMessageID() + "_" + UUID.randomUUID().toString())
                .correlationId(computeCorrelationId(message.getCorrelationID(), message.getMessageID()))
                .creationDateTime(Date.from(clock.instant()))
                .informationSecurityLevel(message.getPayload().getInformationSecurityLevel())
                .informationSensitivity(message.getPayload().getInformationSensitivity())
                .purpose(message.getPayload().getPurpose())
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
                        .ackDetail("Message delivered " + extraMessage);
                if ((message instanceof Push) && (message.getSender().getServiceOperation() == ServiceOperationType.SUBSCRIBE)) {
                    Push pushMessage = (Push) message;
                    if ((pushMessage.getRecipient() == null && pushMessage.getDiscoveryProfiles() == null) ||
                            (pushMessage.getRecipient() == null && pushMessage.getDiscoveryProfiles() != null)
                    ) {
                        List<Service> services = new ArrayList<>();
                        services.add(newService().id("cx.cisesim-nodecx.vessel.subscribe.consumer").build());
                        ackBuilder.addAllDiscoveredServices(services);
                    }
                }

                break;
            case INVALID_SIGNATURE:
                ackBuilder.recipient(newService().id("" + message.getSender().getServiceID()))
                        .sender(newService().id("" + message.getRecipient().getServiceID()))
                        .ackCode(AcknowledgementType.AUTHENTICATION_ERROR)
                        .ackDetail("Message signature not validated: Signature failed core validation.[" + extraMessage.substring(0, Integer.min(extraMessage.length(), 200)) + "...] logged as event UEID:" + ueid);
                break;
            case XML_MALFORMED:
                ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                        .ackDetail("Validation error code: COM-SVC-ERR_001 \n" +
                                " Validation error message: [" + extraMessage.substring(0, Integer.min(extraMessage.length(), 200)) + "...] logged as event UEID:" + ueid);
                break;
            case INTERNAL_ERROR:
                ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                        .ackDetail("Validation error code: COM-SVC-ERR_001 \n" +
                                " Validation error message: [" + extraMessage.substring(0, Integer.min(extraMessage.length(), 200)) + "...] logged as event UEID:" + ueid);
                break;
            case SEMANTIC:
                ackBuilder.ackCode(AcknowledgementType.BAD_REQUEST)
                        .ackDetail("Validation error code: COM-SVC-ERR_001 \n" +
                                " Validation error message: [" + extraMessage.substring(0, Integer.min(extraMessage.length(), 200)) + "...] logged as event UEID:" + ueid);
                break;
        }

        return ackBuilder.build();
    }

    private String computeCorrelationId(String correlationId, String messageId) {
        if (isNullOrEmpty(correlationId)) {
            return messageId;
        }
        return correlationId;
    }


    private XMLGregorianCalendar now() {
        return toXMLGregorianCalendar(Date.from(clock.instant()));
    }

}
