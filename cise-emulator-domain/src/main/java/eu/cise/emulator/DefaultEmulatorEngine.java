package eu.cise.emulator;

import eu.cise.emulator.exceptions.*;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;

import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;

public class DefaultEmulatorEngine implements EmulatorEngine {

    private final Clock clock;
    private final EmuConfig config;
    private final SignatureService signature;

    /**
     * Default constructor that uses UTC as a reference clock
     */
    public DefaultEmulatorEngine(SignatureService signature, EmuConfig config) {
        this(signature, config, Clock.systemUTC());
    }

    /**
     * Constructor that expect a clock as a reference to
     * compute date and time.
     *
     * @param signature the signature service used to sign messages
     * @param config    the domain configuration
     * @param clock     the reference clock
     */
    public DefaultEmulatorEngine(SignatureService signature, EmuConfig config, Clock clock) {
        this.signature = notNull(signature, NullSignatureServiceEx.class);
        this.config = notNull(config, NullConfigEx.class);
        this.clock = notNull(clock, NullClockEx.class);
    }

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        notNull(param, NullSendParamEx.class);
        notNull(message.getSender(), NullSenderEx.class);

        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());
        message.setCorrelationID(param.getCorrelationId());
        message.setCreationDateTime(now());

        if (!isNullOrEmpty(config.serviceId()))
            message.getSender().setServiceID(config.serviceId());

        if (config.serviceType() != null)
            message.getSender().setServiceType(config.serviceType());

        if (config.serviceOperation() != null)
            message.getSender().setServiceOperation(config.serviceOperation());

        // TODO improve signature to use <T extends Message> as a return type
        return (T) signature.sign(message);
    }

    // TODO should be in an helper
    private boolean isNullOrEmpty(String string) {
        return string == null || config.serviceId().isEmpty();
    }

    private XMLGregorianCalendar now() {
        return toXMLGregorianCalendar(Date.from(clock.instant()));
    }

    @Override
    public Acknowledgement send(Message message, SendParam param) {
        return null;
    }
}
