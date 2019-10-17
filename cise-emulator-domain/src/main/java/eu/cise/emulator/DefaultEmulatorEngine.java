package eu.cise.emulator;

import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.exceptions.*;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.helpers.AckBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;
import java.util.UUID;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.emulator.helpers.Asserts.notBlank;
import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.cise.servicemodel.v1.message.AcknowledgementType.SUCCESS;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.ACKNOWLEDGEMENT;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.DateHelper.toDate;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static java.time.temporal.ChronoUnit.HOURS;

public class DefaultEmulatorEngine implements EmulatorEngine {

    private final Clock clock;
    private final EmuConfig emuConfig;
    private final SignatureService signature;
    private Dispatcher dispatcher;
    private XmlMapper prettyNotValidatingXmlMapper;

    /**
     * Default constructor that uses UTC as a reference clock
     * <p>
     * TODO is now clear that this class has way too many
     * responsibilities. It should be split in several classes
     */
    public DefaultEmulatorEngine(
            SignatureService signature,
            Dispatcher dispatcher,
            EmuConfig emuConfig,
            XmlMapper prettyNotValidatingXmlMapper) {

        this(signature, emuConfig, dispatcher, Clock.systemUTC());
        this.dispatcher = dispatcher;
        this.prettyNotValidatingXmlMapper = prettyNotValidatingXmlMapper;
    }

    /**
     * Constructor that expect a clock as a reference to compute date and time.
     *
     * @param signature  the signature service used to sign messages
     * @param emuConfig  the domain configuration
     * @param dispatcher the object used to dispatch the message
     * @param clock      the reference clock
     *                   <p>
     *                   NOTE: this constructor is used only in tests so we do not pass the xml formatter from outside
     */
    DefaultEmulatorEngine(
            SignatureService signature,
            EmuConfig emuConfig,
            Dispatcher dispatcher,
            Clock clock) {

        this.signature = notNull(signature, NullSignatureServiceEx.class);
        this.emuConfig = notNull(emuConfig, NullConfigEx.class);
        this.dispatcher = notNull(dispatcher, NullDispatcherEx.class);
        this.clock = notNull(clock, NullClockEx.class);

        this.prettyNotValidatingXmlMapper = new DefaultXmlMapper.NotValidating();
    }

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        notNull(param, NullSendParamEx.class);
        notNull(message.getSender(), NullSenderEx.class);

        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());
        message.setCorrelationID(
                computeCorrelationId(param.getCorrelationId(), param.getMessageId())
        );

        message.setCreationDateTime(now());


        // TODO improve signature to use <T extends Message> as a return type
        return (T) signature.sign(message);
    }

    @Override
    public Acknowledgement send(Message message) {
        try {
            DispatchResult sendResult = dispatcher.send(message, emuConfig.endpointUrl());

            if (!sendResult.isOK()) {
                throw new EndpointErrorEx();
            }

            Acknowledgement ack = prettyNotValidatingXmlMapper.fromXML(sendResult.getResult());

            return ack;
        } catch (DispatcherException e) {
            throw new EndpointNotFoundEx();
        }
    }

    private boolean areServiceIdAndOperationPresent(Acknowledgement ack) {
        return ack.getSender() == null ||
                isNullOrEmpty(ack.getSender().getServiceID()) ||
                ack.getSender().getServiceOperation() == null;
    }

    @Override
    public Acknowledgement receive(Message message) {
        notNull(message, NullMessageEx.class);
        notBlank(message.getMessageID(), EmptyMessageIdEx.class);

        if (emuConfig.isDateValidationEnabled() &&
                (isMsgDateThreeHoursInThePast(message) || isMsgDateInTheFuture(message))) {

            throw new CreationDateErrorEx();
        }

        // TODO The simulator should be able to receive and show a message
        // and to report errors of the message itself.
        if (message.getSender() == null) {
            throw new NullSenderEx();
        }

        signature.verify(message);

        return buildAck(message);
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
    private Acknowledgement buildAck(Message message) {
/*
        if (!message.getSender().getServiceType().equals(emuConfig.serviceType())) {
            acknowledgementType = SERVICE_TYPE_NOT_SUPPORTED;
            acknowledgementDetail =
                "Supported service type is " + message.getSender().getServiceType().value();
        } else {
            acknowledgementType = SUCCESS;
            acknowledgementDetail = "Message delivered";
        }
*/
        AckBuilder ackBuilder = newAck()
                .id(UUID.randomUUID().toString())
                .sender(newService()
                        .id(message.getSender().getServiceID())
                        .operation(ACKNOWLEDGEMENT))
                .correlationId(computeCorrelationId(message.getCorrelationID(), message.getMessageID()))
                .creationDateTime(Date.from(clock.instant()))
                .informationSecurityLevel(message.getPayload().getInformationSecurityLevel())
                .informationSensitivity(message.getPayload().getInformationSensitivity())
                .purpose(message.getPayload().getPurpose())
                .priority(message.getPriority())
                .ackCode(SUCCESS)
                .ackDetail("Message sent")
                .isRequiresAck(false);

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

    private boolean isMsgDateInTheFuture(Message message) {
        return toDate(message.getCreationDateTime()).after(Date.from(clock.instant()));
    }

    private boolean isMsgDateThreeHoursInThePast(Message message) {
        return toDate(message.getCreationDateTime())
                .before(Date.from(clock.instant().minus(3, HOURS)));
    }

}
