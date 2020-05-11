package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.dto.MessageTypeEnum;
import eu.cise.sim.io.MessagePersistence;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * OCNET-335 Saving messages sent and received
 * The messages sent and received are saved in a file system so they can be easily exported/reused by the user.
 * Clearing the history doesn't delete the messages saved.
 * The messages should be named as follow:
 *
 * date/time (type yyyymmdd-hhmmss)
 * type of message
 * sent/received
 * unique ID to avoid duplicate
 */
public class FileMessageRepository implements MessagePersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMessageRepository.class);

    private final XmlMapper xmlMapper;

    public FileMessageRepository(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public void messageReceived(Message msgRcv) {

    }

    @Override
    public void messageSent(Message msgSent) {

    }

    private String write(Message message, boolean isSent, Date timestamp) throws IOException {

        String fileName = buildFileName(message, isSent, timestamp);
        String xmlMessage = xmlMapper.toXML(message);
        try (BufferedWriter  writer = new BufferedWriter(new FileWriter(fileName))) {

            // TODO add the directory path

            writer.write(xmlMessage);
        } catch (IOException e) {
           throw e;
        }

        return fileName;
    }

    private String buildFileName(Message message, boolean isSent, Date timestamp) {

        String direction = isSent ? "SENT" : "RECV";
        String uuid = message.getMessageID();

        MessageTypeEnum messageType = MessageTypeEnum.valueOf(message);
        String messageTypeName = messageType.name();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");
        String  dateTime = formatter.format(timestamp.toInstant());
        return dateTime + "_" + messageTypeName + "_" + direction + "_" + uuid;
    }

    public static void main(String[] args) {

        Date timestamp = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
        String  dateTime = formatter.format(timestamp);

        System.out.println(dateTime);
    }
}
