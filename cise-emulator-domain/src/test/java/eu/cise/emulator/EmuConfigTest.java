package eu.cise.emulator;


import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmuConfigTest {


    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void it_returns_a_trimmed_value() {
        ConfigWithPreprocessorsTest cfg = ConfigFactory.create(ConfigWithPreprocessorsTest.class);
        assertThat(cfg.testProperty()).isEqualTo("test-property");
    }

    public interface ConfigWithPreprocessorsTest extends EmuConfig {
        @DefaultValue("   test-property  ")
        String testProperty();
    }

}