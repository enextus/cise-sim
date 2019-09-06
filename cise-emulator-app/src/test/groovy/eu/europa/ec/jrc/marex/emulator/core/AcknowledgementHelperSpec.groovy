package eu.europa.ec.jrc.marex.emulator.core

import com.fasterxml.jackson.databind.ObjectMapper
import eu.eucise.xml.DefaultXmlMapper
import eu.eucise.xml.XmlMapper
import eu.europa.ec.jrc.marex.CiseEmulatorApplication
import eu.europa.ec.jrc.marex.emulator.AcknowledgementHelper
import io.dropwizard.cli.Cli
import io.dropwizard.jackson.Jackson
import io.dropwizard.util.JarLocation
import org.junit.Before
import org.junit.Test
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class AcknowledgementHelperSpec extends Specification {



    @Shared
    private  XmlMapper xmlMapper
    @Shared
    private  AcknowledgementHelper acknowledgementHelper

        @Before
        public void simplesetup() throws Exception {
         xmlMapper = new DefaultXmlMapper();
         acknowledgementHelper  = new AcknowledgementHelper();
         }


        @Test
        def "add tag to an incoming acknowledgment xml result in readeable acknolegment message for xmlmapper"() {
            given: " a message with no sender tag"
             String exemplecontent ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\">" +
                    "<CorrelationID>369e1f58-3f9d-4abe-9c9a-c80e21a4b82d</CorrelationID><CreationDateTime>2019-09-02T13:09:14.169Z</CreationDateTime>" +
                    "<MessageID>17f2998d-3375-41b8-ab87-522fef243fc6_7965d883-7584-4772-99f1-73c555acc6b6</MessageID><Priority>High</Priority>" +
                    "<RequiresAck>false</RequiresAck>" +
                    "<AckCode>Success</AckCode>" +
                    "<AckDetail>Message delivered</AckDetail>" +
                    "</ns4:Acknowledgement>\n"
            when: "invoking the xmlmapper within acknowledmenthelper utility method getAckCode "
            String AckCode = acknowledgementHelper.getAckCode(xmlMapper, exemplecontent);

            then: "do return the value/content of the AckCode tag"
            AckCode=="SUCCESS"
        }

    def "Name"() {
    }

    @Test
    def "produce a acknowledgement with a special tag sender"() {
        given: " a message with no sender tag"
        String exemplecontent ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\">" +
                "<CorrelationID>369e1f58-3f9d-4abe-9c9a-c80e21a4b82d</CorrelationID><CreationDateTime>2019-09-02T13:09:14.169Z</CreationDateTime>" +
                "<MessageID>17f2998d-3375-41b8-ab87-522fef243fc6_7965d883-7584-4772-99f1-73c555acc6b6</MessageID><Priority>High</Priority>" +
                "<RequiresAck>false</RequiresAck>" +
                "<AckCode>Success</AckCode>" +
                "<AckDetail>Message delivered</AckDetail>" +
                "</ns4:Acknowledgement>\n"
        when: "invoking the acknowledgment helper utility method to return a text that contains sender tag "
        String ResultContent = acknowledgementHelper.increaseAckCodeWithSender(exemplecontent);

        then: "do return value that contains the expected tags (sender, service tags)"
        ResultContent.contains("<Sender>")
        ResultContent.contains("</Sender>")
        ResultContent.contains("<Service")
    }
    }



