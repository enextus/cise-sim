package eu.cise.sim;

import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.DefaultSimEngine;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.engine.SimEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.mockito.Mockito.*;

public class AddingSignatureTest {

    private SimEngine engine;
    private SignatureService signatureService;
    private Push push;
    private Dispatcher dispatcher;
    private SimConfig config;

    @Before
    public void before() {
        config = mock(SimConfig.class);
        signatureService = mock(SignatureService.class);
        dispatcher = mock(Dispatcher.class);
        engine = new DefaultSimEngine(signatureService, dispatcher, config);
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
        push.getSender().setServiceType(ServiceType.VESSEL_SERVICE);

        engine.receive(push);

        verify(signatureService).verify(push);
    }

    private SendParam params() {
        return new SendParam(false, "msgId", "corrId");
    }

}
