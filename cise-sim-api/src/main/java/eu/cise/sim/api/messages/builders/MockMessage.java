package eu.cise.sim.api.messages.builders;

import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MockMessage {

    private static final XmlMapper XML_MAPPER =  new DefaultXmlMapper.PrettyNotValidating();
    private static final String TEMPLATE_PATH = "templates/messages";

    public static Message getPullMessage() throws IOException {

        String message =  readResource("Incident_mockTemplate.xml");

       return XML_MAPPER.fromXML(message);
    }



    private static String readResource(String resourceName) throws IOException {
        Path path = getFilePath(resourceName);
        return Files.readString(path);
    }

    private static URI getResourceURI(String resourceDir) {
        try {
            return MockMessage.class.getClassLoader().getResource(resourceDir).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path getFilePath(String fileName) {
        return Paths.get(TEMPLATE_PATH + "/" + fileName);
    }


    public static void main(String[] args) throws IOException {

        Message m = MockMessage.getPullMessage();
        System.out.println(m);
    }
}
