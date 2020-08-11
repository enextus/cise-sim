package eu.cise.sim.dropw;

import eu.cise.sim.dropw.context.AppContext;
import eu.cise.sim.dropw.context.DefaultAppContext;
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