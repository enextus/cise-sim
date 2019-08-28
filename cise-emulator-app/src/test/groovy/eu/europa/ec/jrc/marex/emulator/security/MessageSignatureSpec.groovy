package eu.europa.ec.jrc.marex.emulator.security

import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration
import eu.europa.ec.jrc.marex.emulator.CiseEmulatorConfigurationMock
import eu.europa.ec.jrc.marex.resources.InboundRESTMessageService
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Rule
import org.junit.Test



class MessageSignatureSpec extends spock.lang.Specification {


    CiseEmulatorConfiguration ciseEmulatorConfiguration = CiseEmulatorConfigurationMock.getMinimalCliServerConfiguration();
    InboundRESTMessageService inboundRESTMessageService = new InboundRESTMessageService(ciseEmulatorConfiguration)

    @Rule
    ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(inboundRESTMessageService)
            .build()


    @Test
    def "Resource successfully receive welformed message and return acknowledgement"() {
        given: "message content to be received is signed by specific key"

        when: "message is received by the resource that use certificate from a jks with a password "

        then: "response ackcode should be success"
    }
}