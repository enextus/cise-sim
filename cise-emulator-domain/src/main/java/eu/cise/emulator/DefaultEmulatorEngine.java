package eu.cise.emulator;

import com.google.common.base.Strings;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.emulator.exceptions.*;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;
import static eu.eucise.helpers.ServiceBuilder.newService;

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
    public Acknowledgement send(Message message) {
        Acknowledgement response;
        try {
            DispatchResult sendResult = dispatcher.send(message, config.endpointUrl());

            if (!sendResult.isOK()) {
                throw new EndpointErrorEx();
            }

            String result = sendResult.getResult();
            LOGGER.debug("send in DefaultEmulatorEngine receive result {}", result);
            DefaultXmlMapper notValidatingXmlMapper = new DefaultXmlMapper.NotValidating();
            Acknowledgement acknowledgement = notValidatingXmlMapper.fromXML(result);

            if (acknowledgement.getSender() == null ||
                    Strings.isNullOrEmpty(acknowledgement.getSender().getServiceID()) ||
                    acknowledgement.getSender().getServiceOperation() == null
            ) {
                acknowledgement.setSender(newService().id("").operation(ServiceOperationType.PUSH).build());
            }
            response = acknowledgement;
        } catch (DispatcherException e) {
            throw new EndpointNotFoundEx();
        }

        return response;
    }

    @Override
    public void receive(Message message) {
        notNull(message, NullMessageEx.class);

        XMLGregorianCalendar messageXmlGregorianCalendar = message.getCreationDateTime();

        GregorianCalendar currentGregorianCalendar = new GregorianCalendar();
        currentGregorianCalendar.setTime(java.util.Date.from(java.time.ZonedDateTime.now(ZoneId.of("UTC")).minusHours(3).toInstant()));
        XMLGregorianCalendar currentXmlGregorianCalendar = new XMLGregorianCalendarImpl(currentGregorianCalendar);

        if (messageXmlGregorianCalendar.compare(currentXmlGregorianCalendar) == DatatypeConstants.LESSER) {
            throw new CreationDateErrorEx();
        }

    }
}
