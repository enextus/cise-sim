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
