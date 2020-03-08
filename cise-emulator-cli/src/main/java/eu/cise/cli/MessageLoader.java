package eu.cise.cli;

import eu.cise.cli.exceptions.FileLoaderEx;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
}
