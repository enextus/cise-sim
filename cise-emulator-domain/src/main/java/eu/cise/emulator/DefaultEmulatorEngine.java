package eu.cise.emulator;

import eu.cise.emulator.exceptions.NullSendParamEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;

import static eu.cise.emulator.helpers.Asserts.notNull;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;

public class DefaultEmulatorEngine implements EmulatorEngine {

    private final Clock clock;
    private final SignatureService signature;

    /**
     * Default constructor that uses UTC as a reference clock
     */
    public DefaultEmulatorEngine(SignatureService signature) {
        this.signature = signature;
        this.clock = Clock.systemUTC();
    }

    /**
     * Constructor that expect a clock as a reference to
     * compute date and time.
     *
     * @param clock the reference clock
     */
    public DefaultEmulatorEngine(SignatureService signature, Clock clock) {
        this.signature = signature;
        this.clock = clock;
    }

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        notNull(param, NullSendParamEx.class);

        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());
        message.setCorrelationID(param.getCorrelationId());
        message.setCreationDateTime(now());

        // TODO improve signature to use <T extends Message> as a return type
        return (T) signature.sign(message);
    }

    private XMLGregorianCalendar now() {
        return toXMLGregorianCalendar(Date.from(clock.instant()));
    }

    @Override
    public Acknowledgement send(Message message, SendParam param) {
        return null;
    }
}
