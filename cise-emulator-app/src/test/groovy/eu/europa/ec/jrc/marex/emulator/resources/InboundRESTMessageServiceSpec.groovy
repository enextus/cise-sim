package eu.europa.ec.jrc.marex.resources

import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration
import eu.europa.ec.jrc.marex.emulator.CiseEmulatorConfigurationMock
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import spock.lang.Specification

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors
import java.util.stream.Stream


@Ignore("TODO")
class InboundRESTMessageServiceSpec extends Specification {


    CiseEmulatorConfiguration ciseEmulatorConfiguration = CiseEmulatorConfigurationMock.getMinimalCliServerConfiguration();
    InboundRESTMessageService inboundRESTMessageService = new InboundRESTMessageService(ciseEmulatorConfiguration)

    @Rule
    ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(inboundRESTMessageService)
            .build()


    @Test
    def "Resource successfully receive welformed message and return acknowledgement"() {
        given: "message content come from single known ok messageTemplate"
        Path path = Paths.get(getClass().getClassLoader().getResource("xmlmessages/PushTemplate.xml").toURI());
        Stream<String> lines = Files.lines(path);
        String msg = lines.collect(Collectors.joining("\n"));
        lines.close();

        // new SourceBufferFileSource().getReferenceFileContent("").toString()
        when: "message is sent to the resource"
        Response response = resources.client().target("/emu/rest/CISEMessageServiceREST")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(msg, MediaType.APPLICATION_XML_TYPE))
        then: "response should be ok"
        response.getStatusInfo() == Response.Status.OK
    }

    @Test
    def "Resource successfully accept non signed message returning acknowledgement with proper 'ackcode' tag"() {
        given: "message content come from single known ok messageTemplate"
        Path path = Paths.get(getClass().getClassLoader().getResource("xmlmessages/PushTemplatefail.xml").toURI());
        Stream<String> lines = Files.lines(path);
        String msg = lines.collect(Collectors.joining("\n"));
        lines.close();

        // new SourceBufferFileSource().getReferenceFileContent("").toString()
        when: "message is sent to the resource"
        Response response = resources.client().target("/emu/rest/CISEMessageServiceREST")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(msg, MediaType.APPLICATION_XML_TYPE))

        then: "response should be ok"
        response.getStatusInfo() == Response.Status.OK
    }


}



