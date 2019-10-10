package eu.cise.emulator;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.mockito.Mockito.*;

public class AddingSignatureTest {

    private EmulatorEngine engine;
    private SignatureService signatureService;
    private Push push;
    private Dispatcher dispatcher;
    private EmuConfig config ;

    @Before
    public void before() {
        config = mock(EmuConfig.class);
        signatureService = mock(SignatureService.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultEmulatorEngine(signatureService, dispatcher, config);
        push = newPush().id("aMessageId").sender(newService()).build();
    }

    @After
    public void after() {
        reset(config);
        reset(signatureService);
        reset(dispatcher);
    }

    @Test
    public void it_signs_the_message() {
        engine.prepare(push, params());

        verify(signatureService).sign(push);
    }

    @Test
    public void it_verify_the_signature() {
        when(config.isDateValidationEnabled()).thenReturn(Boolean.FALSE);

        push.getSender().setServiceType(ServiceType.VESSEL_SERVICE);

        engine.receive(push);

        verify(signatureService).verify(push);
    }

    private SendParam params() {
        return new SendParam(false, "msgId", "corrId");
    }

}
