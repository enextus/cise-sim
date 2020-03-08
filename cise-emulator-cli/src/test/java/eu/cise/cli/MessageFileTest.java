package eu.cise.cli;

import eu.cise.servicemodel.v1.message.Push;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MessageFileTest {
    private MessageLoader messageLoader;
    private XmlMapper xmlMapper;

    @Before
    public void before() throws IOException {
        xmlMapper = new DefaultXmlMapper();
        messageLoader = new MessageLoader(xmlMapper);
        Files.copy(resourceFile("/test.string"), pathTo("test.string"), REPLACE_EXISTING);
        Files.copy(resourceFile("/PushTemplate.xml"), pathTo("PushTemplate.xml"), REPLACE_EXISTING);
    }

    @Test
    public void it_transform_a_file_in_a_string() {
        String actual = messageLoader.loadAsString(pathTo("test.string").toString());

        assertThat(actual, is("first-line\nsecond-line"));
    }

    @Test
    public void it_transform_a_file_in_a_string_relative_dir() {
        Push actual = messageLoader.load("src/test/resources/PushTemplate.xml");

        assertThat(actual, equalTo(xmlMapper.fromXML(resourceToString("/PushTemplate.xml"))));
    }


    @Test
    public void it_transform_a_file_in_a_message() {
        Push actual = messageLoader.load(pathTo("PushTemplate.xml").toString());

        assertThat(actual, equalTo(xmlMapper.fromXML(resourceToString("/PushTemplate.xml"))));
    }

    private Path pathTo(String filename) {
        return Paths.get(systemTempDir(), filename);
    }

    private InputStream resourceFile(String name) {
        return getClass().getResourceAsStream(name);
    }

    private String systemTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    private String resourceToString(String filename) {
        try {
            return Files.readString(resourceToPath(filename));
        } catch (Exception e) {
            fail("can't read file " + filename);
            throw new RuntimeException("should never happen");
        }
    }

    private Path resourceToPath(String filename) {
        try {
            return Paths.get(getClass().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            fail("can't read file " + filename);
            throw new RuntimeException("should never happen");
        }
    }
}
