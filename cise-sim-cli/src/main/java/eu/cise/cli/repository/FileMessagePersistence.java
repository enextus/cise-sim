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

package eu.cise.cli.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.io.MessagePersistence;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OCNET-335 Saving messages sent and received
 * The messages sent and received are saved in a file system so they can be easily exported/reused by the user.
 * Clearing the history doesn't delete the messages saved.
 * The messages should be named as follow:
 * <p>
 * date/time (type yyyymmdd-hhmmss)
 * type of message
 * sent/received
 * unique ID to avoid duplicate*
 */
public class FileMessagePersistence implements MessagePersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMessagePersistence.class);

    private final XmlMapper xmlMapper;
    private final String repositoryDir;

    public FileMessagePersistence(XmlMapper xmlMapper, String repositoryDir) {
        this.xmlMapper = xmlMapper;
        this.repositoryDir = new File(repositoryDir).getAbsolutePath();
    }

    @Override
    public void messageReceived(Message msgRcv) {

        try {

            Date timestamp = new Date();
            write(msgRcv, Boolean.FALSE, timestamp);

        } catch (IOException e) {
            LOGGER.warn("messageReceived writing error : {}", e.getMessage());
        }

    }

    @Override
    public void messageSent(Message msgSent) {

        try {

            Date timestamp = new Date();
            write(msgSent, Boolean.TRUE, timestamp);

        } catch (IOException e) {
            LOGGER.warn("messageSent     writing error : {}", e.getMessage());
        }

    }


    private FileNameRepository write(Message message, boolean isSent, Date timestamp) throws IOException {

        FileNameRepository fileNameRepository = FileNameRepository.getInstance(message, isSent, timestamp);

        String fileName = fileNameRepository.getFileName();
        String xmlMessage = xmlMapper.toXML(message);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(repositoryDir + File.separatorChar + fileName))) {

            writer.write(xmlMessage);
        }

        LOGGER.info("write xml file {}", fileName);

        return fileNameRepository;
    }


    /**
     * File name builder
     */
    static class FileNameRepository {

        private static final String TIMESTAMP_FORMAT = "yyyyMMdd-HHmmssSSS";
        private static final String ITEM_SEPARATOR = "_";

        private static final String MSG_SENT = "SENT";
        private static final String MSG_RECV = "RECV";

        private final String uuid;
        private final boolean isSent;
        private final String messageTypeName;
        private final Date timestamp;

        private final String fileName;

        private FileNameRepository(String uuid, boolean isSent, String messageTypeName, Date timestamp, String fileName) {
            this.uuid = uuid;
            this.isSent = isSent;
            this.messageTypeName = messageTypeName;
            this.timestamp = timestamp;
            this.fileName = fileName;
        }


        public static FileNameRepository getInstance(String fileName) throws ParseException {

            String[] msgElement = fileName.split(ITEM_SEPARATOR);
            if (msgElement.length != 4) {
                throw new ParseException("File name not in the right format : " + fileName, 0);
            }
            SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
            Date timestamp = formatter.parse(msgElement[0]);
            String messageTypeName = msgElement[1];
            boolean isSent = msgElement[2].equals(MSG_SENT);
            String uuid = msgElement[3];

            return new FileNameRepository(uuid, isSent, messageTypeName, timestamp, fileName);
        }


        public static FileNameRepository getInstance(Message message, boolean isSent, Date timestamp) {

            String uuid = UUID.randomUUID().toString();

            String direction = isSent ? MSG_SENT : MSG_RECV;

            MessageTypeEnum messageType = MessageTypeEnum.valueOf(message);
            String messageTypeName = messageType.getFileName();

            String fileName = buildFilename(uuid, direction, messageTypeName, timestamp);

            return new FileNameRepository(uuid, isSent, messageTypeName, timestamp, fileName);
        }

        private static String buildFilename(String uuid, String direction, String messageTypeName, Date timestamp) {

            SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
            String dateTime = formatter.format(timestamp);

            return dateTime + ITEM_SEPARATOR
                + messageTypeName + ITEM_SEPARATOR
                + direction + ITEM_SEPARATOR
                + uuid
                + ".xml";
        }

        public String getUuid() {
            return uuid;
        }

        public boolean isSent() {
            return isSent;
        }

        public String getMessageTypeName() {
            return messageTypeName;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
