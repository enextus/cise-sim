package eu.cise.emulator;

import eu.cise.emulator.api.CiseEmulatorAPI;
import eu.cise.servicemodel.v1.message.Message;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AppContextTest {

    private AppContext appContext;


    @Before
    public void before() {

        appContext = new DefaultAppContext();
    }

    @Test
    public void it_builds_message_processor() {
        MessageProcessor messageProcessorConcrete = appContext.makeMessageProcessor();

        assertThat(messageProcessorConcrete).isNotNull();
    }

    @Test
    public void it_builds_web_api() {
        AppContext appContext = new DefaultAppContext();
        MessageProcessor messageProcessor = mock(MessageProcessor.class);

        CiseEmulatorAPI api = appContext.makeEmulatorApi(messageProcessor);

        assertThat(api).isNotNull();
    }

    @Test
    public void it_connects_API_to_MessageProcesor() {
        EmulatorEngine engine = mock(EmulatorEngine.class);
        MessageProcessor messageProcessor = new DefaultMessageProcessor(engine);

        AppContext appContext = new DefaultAppContext();

        CiseEmulatorAPI resourceApi = appContext.makeEmulatorApi(messageProcessor);
        String[] command = {"server", "config.yml"};
        try {
            resourceApi.run(command);
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(engine).prepare(any(Message.class), any(SendParam.class));
    }

}