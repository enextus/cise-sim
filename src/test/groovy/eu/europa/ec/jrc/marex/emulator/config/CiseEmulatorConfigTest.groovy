package eu.europa.ec.jrc.marex.config;
import io.dropwizard.jackson.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import spock.lang.Specification;

public class CiseEmulatorConfigTest  extends Specification{

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void ConfigValueExpectationTest() throws Exception {
         Object person;
         //MAPPER.readValue(fixture("fixtures/person.json"), EmulatorConfig.class));
        expect:
        1 + 1 ==2 ;
    }
}

// backyard:
//create an serialized object  1: final EmulatorConfig emulatorConfig = new EmulatorConfig("Luther Blissett", "lb@example.com");
//create an serialized object 2 : final String expected = MAPPER.writeValueAsString( xxxx );