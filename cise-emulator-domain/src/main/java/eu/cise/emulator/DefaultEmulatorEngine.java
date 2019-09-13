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

    public DefaultEmulatorEngine(Clock clock) {
        this.clock = clock;
    }

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        notNull(param, NullSendParamEx.class);

        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());
        message.setCorrelationID(param.getCorrelationId());
        message.setCreationDateTime(now());

        return message;
    }

    private XMLGregorianCalendar now() {
        return toXMLGregorianCalendar(Date.from(clock.instant()));
    }

    @Override
    public Acknowledgement send(Message message, SendParam param) {
        return null;
    }

}
