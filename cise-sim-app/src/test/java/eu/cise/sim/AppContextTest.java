package eu.cise.sim;

import eu.cise.sim.app.AppContext;
import eu.cise.sim.app.DefaultAppContext;
import eu.cise.sim.engine.MessageProcessor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppContextTest {

    private AppContext appContext;

    @Before
    public void before() {
        appContext = new DefaultAppContext();
    }

    @Ignore
    @Test
    public void it_builds_message_processor() {
        MessageProcessor messageProcessorConcrete = appContext.makeMessageProcessor();

        assertThat(messageProcessorConcrete).isNotNull();
    }

}