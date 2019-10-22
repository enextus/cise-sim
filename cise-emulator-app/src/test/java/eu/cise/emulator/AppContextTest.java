package eu.cise.emulator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

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

}