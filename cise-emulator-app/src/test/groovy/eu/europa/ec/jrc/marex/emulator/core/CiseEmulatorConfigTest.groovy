package eu.europa.ec.jrc.marex.emulator.core

import com.fasterxml.jackson.databind.ObjectMapper
import io.dropwizard.jackson.Jackson
import org.junit.Test
import spock.lang.Specification

public class CiseEmulatorConfigTest extends Specification{

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void ConfigValueExpectationTest() throws Exception {
        byte[] decodeddata= Base64.getDecoder().decode("ZXlKcFpIVnpaWElpT2lKdWRXeHNJaXdpYVc1a1pYZ2lPakVzSW1SbGMyTnlhWEIwYVc5dUlqb2lWR1Z6ZENCaGNtVmhJaXdpWVdOMGFXOXVJam9pWVdSa1lYSmxZU0lzSW5SNWNHVWlPaklzSW1WdWRHbDBlU0k2SW1GeVpXRnpJaXdpZEdsdFpYTjBZVzF3SWpveE1qTTBOVFkzT0RsOQ==");
        String test= new String(decodeddata);
        System.out.println(test);
    }
}

// backyard:
//create an serialized object  1: final EmulatorConfig emulatorConfig = new EmulatorConfig("Luther Blissett", "lb@example.com");
//create an serialized object 2 : final String expected = MAPPER.writeValueAsString( xxxx );