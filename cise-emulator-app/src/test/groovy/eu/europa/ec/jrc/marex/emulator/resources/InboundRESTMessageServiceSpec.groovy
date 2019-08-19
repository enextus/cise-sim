package eu.europa.ec.jrc.marex.resources

import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration
import eu.europa.ec.jrc.marex.emulator.CiseEmulatorConfigurationMock
import eu.europa.ec.jrc.marex.marex.resources.InboundRESTMessageService
import io.dropwizard.Configuration
import io.dropwizard.testing.junit.ResourceTestRule
import org.hibernate.validator.constraints.NotEmpty
import org.junit.ClassRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import spock.lang.Specification

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import static org.assertj.core.api.Assertions.assertThat


@Ignore("TODO")
class InboundRESTMessageServiceSpec extends Specification {


    CiseEmulatorConfiguration ciseEmulatorConfiguration = CiseEmulatorConfigurationMock.getMinimalCliServerConfiguration();
    InboundRESTMessageService inboundRESTMessageService = new InboundRESTMessageService(ciseEmulatorConfiguration)

    @Rule
    ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(inboundRESTMessageService)
            .build()


    @Test
    def "on success cli sender create 2 files in output directory : one with the sent message and one with the acknowledgement"() {
        given: "a post is sent with wellformed message"
        String msg = ""
        when: " message is sent to the resource"
        Response response = resources.client().target("/")
                .request(MediaType.APPLICATION_XML_TYPE)
                .post(Entity.entity(msg, MediaType.APPLICATION_XML_TYPE))
        then: " response is ok  AND a file have been created in the output directory"
        response.getStatusInfo() == Response.Status.OK
        //1 * fileStore.save(_ as File) >> File.createTempFile()
        true == true
    }

    @Test
    def "on fail connection cli sender create 2 files in output directory : one with the sent message and one with the error log"() {
        given: "a post is sent with wellformed message"
        String msg = ""
        when: " message is sent to the resource"
        Response response = resources.client().target("/")
                .request(MediaType.APPLICATION_XML_TYPE)
                .post(Entity.entity(msg, MediaType.APPLICATION_XML_TYPE))
        then: " response is ok  AND a file have been created in the output directory"
        response.getStatusInfo() == Response.Status.OK
        //1 * fileStore.save(_ as File) >> File.createTempFile()
        true == true
    }
}



