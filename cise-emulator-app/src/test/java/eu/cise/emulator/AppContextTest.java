package eu.cise.emulator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppContextTest {


    @Test
    public void it_builds_message_processor() {
        AppContext appContext = new DefaultAppContext();

        MessageProcessor messageProcessor= appContext.makeMessageProcessor();

        assertThat(messageProcessor).isNotNull();
    }
}