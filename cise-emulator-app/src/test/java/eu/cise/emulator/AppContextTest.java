package eu.cise.emulator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import eu.cise.emulator.api.CiseEmulatorApi;

public class AppContextTest {

    @Test
    public void it_builds_message_processor() {
        AppContext appContext = new DefaultAppContext();

        MessageProcessor messageProcessor= appContext.makeMessageProcessor();

        assertThat(messageProcessor).isNotNull();
    }

    @Test
    public void it_builds_web_api() {
        AppContext appContext = new DefaultAppContext();

        CiseEmulatorApi api= appContext.makeEmulatorApi();

        assertThat(api).isNotNull();
    }

}