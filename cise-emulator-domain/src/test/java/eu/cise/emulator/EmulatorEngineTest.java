package eu.cise.emulator;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmulatorEngineTest {
    @Test
    public void it_xxx() {
        assertThat(1, is(1));
    }

    @Test
    public void it_mocks() {
        EmulatorEngine engine = mock(EmulatorEngine.class);

        engine.send(null);

        verify(engine).send(any());
    }
}
