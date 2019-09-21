package eu.cise.emulator;

import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.exceptions.*;
import eu.cise.emulator.helpers.AcknowledgementHelper;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;

import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;

public class DefaultEmulatorEngine implements EmulatorEngine {

    private static final String SENDER_TAG = "<Sender>";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmulatorEngine.class);
    private final Clock clock;
    private final EmuConfig config;
    private final SignatureService signature;
    private Dispatcher dispatcher;
    private XmlMapper xmlMapper;

    /**
     * Default constructor that uses UTC as a reference clock
     */
    public DefaultEmulatorEngine(SignatureService signature, Dispatcher dispatcher, EmuConfig config) {
        this(signature, config, dispatcher, Clock.systemUTC());
        this.dispatcher = dispatcher;
        xmlMapper = new DefaultXmlMapper();
    }

    /**
     * Constructor that expect a clock as a reference to
     * compute date and time.
     *
     * @param signature  the signature service used to sign messages
     * @param config     the domain configuration
     * @param dispatcher
     * @param clock      the reference clock
     */
    public DefaultEmulatorEngine(SignatureService signature, EmuConfig config, Dispatcher dispatcher, Clock clock) {
        this.signature = notNull(signature, NullSignatureServiceEx.class);
        this.config = notNull(config, NullConfigEx.class);
        this.dispatcher = notNull(dispatcher, NullDispatcherEx.class);
        this.clock = notNull(clock, NullClockEx.class);
    }

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        notNull(param, NullSendParamEx.class);
        notNull(message.getSender(), NullSenderEx.class);

        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());

        if (isNullOrEmpty(param.getCorrelationId())) {
            message.setCorrelationID(param.getMessageId());
        } else {
            message.setCorrelationID(param.getCorrelationId());
        }

        message.setCreationDateTime(now());

        if (!isNullOrEmpty(config.serviceId())) {
            message.getSender().setServiceID(config.serviceId());
        }

        if (config.serviceType() != null) {
            message.getSender().setServiceType(config.serviceType());
        }

        if (config.serviceOperation() != null) {
            message.getSender().setServiceOperation(config.serviceOperation());
        }

        // TODO improve signature to use <T extends Message> as a return type
        return (T) signature.sign(message);
    }

    // TODO should be in an helper
    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    private XMLGregorianCalendar now() {
        return toXMLGregorianCalendar(Date.from(clock.instant()));
    }

    @Override
    public Acknowledgement send(Message message) throws EndpointNotFoundEx, EndpointErrorEx {
        Acknowledgement response;
        try {
            DispatchResult sendResult = dispatcher.send(message, config.endpointUrl());

            if (!sendResult.isOK()) {
                throw new EndpointErrorEx();
            }

            String result = sendResult.getResult();
            LOGGER.debug("send in DefaultEmulatorEngine receive result {}", result);
            if (!result.contains(SENDER_TAG)) {
                result = AcknowledgementHelper.increaseAckCodeWithSender(result);
            }

            response = xmlMapper.fromXML(result);
        } catch (DispatcherException e) {
            throw new EndpointNotFoundEx();
        }

        return response;
    }
}
