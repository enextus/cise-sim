package eu.cise.sim.api.messages.builders;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MockMessage {

    private static final XmlMapper XML_MAPPER =  new DefaultXmlMapper.PrettyNotValidating();
    private static final String TEMPLATE_PATH = "templates/messages";

    public static Message getIncidentMessage() throws IOException {

        String message =  readResource("Incident_mockTemplate.xml");

       return XML_MAPPER.fromXML(message);
    }

    public static PullRequest getDiscoveryMessage() throws IOException {

        String message =  readResource("Discovery_mockTemplate.xml");

        return XML_MAPPER.fromXML(message);
    }

    public static Acknowledgement getDiscoveryAckSynch() throws IOException {

        String message =  readResource("AckSynchDiscovery_mockTemplate.xml");

        return XML_MAPPER.fromXML(message);
    }


    private static String readResource(String resourceName) throws IOException {
        Path path = getFilePath(resourceName);
        return Files.readString(path);
    }

    private static Path getFilePath(String fileName) {
        return Paths.get(TEMPLATE_PATH + "/" + fileName);
    }

}
