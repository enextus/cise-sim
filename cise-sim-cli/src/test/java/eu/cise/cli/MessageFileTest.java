/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

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
