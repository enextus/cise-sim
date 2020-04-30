package eu.cise.sim.engine;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.sim.helpers.Asserts.notBlank;
import static eu.cise.sim.helpers.Asserts.notNull;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;

import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementFactory;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementType;
import eu.cise.sim.exceptions.EmptyMessageIdEx;
import eu.cise.sim.exceptions.EndpointErrorEx;
import eu.cise.sim.exceptions.EndpointNotFoundEx;
import eu.cise.sim.exceptions.NullClockEx;
import eu.cise.sim.exceptions.NullConfigEx;
import eu.cise.sim.exceptions.NullDispatcherEx;
import eu.cise.sim.exceptions.NullMessageEx;
import eu.cise.sim.exceptions.NullSendParamEx;
import eu.cise.sim.exceptions.NullSenderEx;
import eu.cise.sim.exceptions.NullSignatureServiceEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import java.sql.Date;
import java.time.Clock;
import javax.xml.datatype.XMLGregorianCalendar;


public class DefaultSimEngine implements SimEngine {

    private final Clock clock;
    private final SimConfig simConfig;
    private final SignatureService signature;
    private Dispatcher dispatcher;
    private SynchronousAcknowledgementFactory acknowledgementFactory;

    /**
     * Default constructor that uses UTC as a reference clock
     * <p>
     * TODO is now clear that this class has way too many responsibilities. It should be split in
     * several classes
     */
    public DefaultSimEngine(
        SignatureService signature,
        Dispatcher dispatcher,
        SimConfig simConfig) {

        this(signature, simConfig, dispatcher, Clock.systemUTC());
        this.dispatcher = dispatcher;

        // Every time there is a new it should be where all the constructions are happening
        this.acknowledgementFactory = new SynchronousAcknowledgementFactory();
    }

    /**
     * Constructor that expect a clock as a reference to compute date and time.
     *
     * @param signature  the signature service used to sign messages
     * @param simConfig  the domain configuration
     * @param dispatcher the object used to dispatch the message
     * @param clock      the reference clock
     *                   <p>
     *                   NOTE: this constructor is used only in tests
     */
    DefaultSimEngine(
        SignatureService signature,
        SimConfig simConfig,
        Dispatcher dispatcher,
        Clock clock) {

        this.signature = notNull(signature, NullSignatureServiceEx.class);
        this.simConfig = notNull(simConfig, NullConfigEx.class);
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
            DispatchResult sendResult = dispatcher.send(message, simConfig.destinationUrl());

            if (!sendResult.isOK()) {
                throw new EndpointErrorEx();
            }

            return sendResult.getResult();
        } catch (DispatcherException e) {
            throw new EndpointNotFoundEx();
        }
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
