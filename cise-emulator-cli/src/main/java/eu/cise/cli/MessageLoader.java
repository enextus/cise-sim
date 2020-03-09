package eu.cise.cli;

import eu.cise.cli.exceptions.FileLoaderEx;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MessageLoader {
    private final XmlMapper xmlMapper;

    public MessageLoader(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public String loadAsString(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            throw new FileLoaderEx(e);
        }
    }

    public <T extends Message> T load(String filename) {
        return xmlMapper.fromXML(loadAsString(filename));
    }

    public void saveSentMessage(Message messageLoaded) {
        saveMessage(messageLoaded, "msg-", trimUUID(messageLoaded.getMessageID()));
    }

    public void saveReturnedAck(Acknowledgement returnedAck) {
        saveMessage(returnedAck, "ack-", trimUUID(returnedAck.getCorrelationID()));
    }

    private void saveMessage(Message messageLoaded, String prefix, String filename) {
        try {
            Files.write(Paths.get("sent", prefix + filename), xmlMapper.toXML(messageLoaded).getBytes(UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String trimUUID(String id) {
        return id.substring(0, 8);
    }
}
