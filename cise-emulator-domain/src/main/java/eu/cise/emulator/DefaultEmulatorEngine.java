package eu.cise.emulator;

import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.SynchronousAcknowledgement.SynchronousAcknowledgementFactory;
import eu.cise.emulator.SynchronousAcknowledgement.SynchronousAcknowledgementType;
import eu.cise.emulator.exceptions.*;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.emulator.helpers.Asserts.notBlank;
import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;


public class DefaultEmulatorEngine implements EmulatorEngine {

    private final Clock clock;
    private final EmuConfig emuConfig;
    private final SignatureService signature;
    private Dispatcher dispatcher;
    private SynchronousAcknowledgementFactory acknowledgementFactory;

    /**
     * Default constructor that uses UTC as a reference clock
     * <p>
     * TODO is now clear that this class has way too many
     * responsibilities. It should be split in several classes
     */
    public DefaultEmulatorEngine(
            SignatureService signature,
            Dispatcher dispatcher,
            EmuConfig emuConfig) {

        this(signature, emuConfig, dispatcher, Clock.systemUTC());
        this.dispatcher = dispatcher;
        this.acknowledgementFactory = new SynchronousAcknowledgementFactory();
    }

    /**
     * Constructor that expect a clock as a reference to compute date and time.
     *
     * @param signature  the signature service used to sign messages
     * @param emuConfig  the domain configuration
     * @param dispatcher the object used to dispatch the message
     * @param clock      the reference clock
     *                   <p>
     *                   NOTE: this constructor is used only in tests
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
            DispatchResult sendResult = dispatcher.send(message, emuConfig.destinationUrl());

            if (!sendResult.isOK()) {
                throw new EndpointErrorEx();
            }

            return sendResult.getResult();
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

        // TODO The simulator should be able to receive and show a message
        // and to report errors of the message itself.
        if (message.getSender() == null) {
            throw new NullSenderEx();
        }

        signature.verify(message);


        return acknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.SUCCESS, "");
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
