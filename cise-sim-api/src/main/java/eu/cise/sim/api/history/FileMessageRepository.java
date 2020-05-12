package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.dto.MessageTypeEnum;
import eu.cise.sim.io.MessagePersistence;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import static eu.cise.sim.api.history.MemoryQueuedRepository.MSG_RECV;
import static eu.cise.sim.api.history.MemoryQueuedRepository.MSG_SENT;


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
    private final String    repositoryDir;
    private final int       maxListDimension = 10; // todo OCNET 332 wil substuite this with configuration param

    private final MessageStack messageStack;

    public FileMessageRepository(XmlMapper xmlMapper, String repositoryDir) {
        this.xmlMapper = xmlMapper;
        this.repositoryDir = new File(repositoryDir).getAbsolutePath();
        this.messageStack = new MessageStack(maxListDimension);
    }

    @Override
    public void messageReceived(Message msgRcv) {

        try {

            Date timestamp = new Date();
            String fileName = write(msgRcv, Boolean.FALSE, timestamp);

            messageStack.add(MessageShortInfoDto.getInstance(msgRcv, MSG_RECV, timestamp), fileName);

        } catch (IOException e) {
            LOGGER.warn("messageReceived writing error : {}", e.getMessage());
        }

    }

    @Override
    public void messageSent(Message msgSent) {

        try {

            Date timestamp = new Date();
            String fileName = write(msgSent, Boolean.TRUE, timestamp);

            messageStack.add(MessageShortInfoDto.getInstance(msgSent, MSG_SENT, timestamp), fileName);

        } catch (IOException e) {
            LOGGER.warn("messageSent writing error : {}", e.getMessage());
        }

    }

    public List<MessageShortInfoDto> getShortInfoAfter(long timestamp) {

        List<MessageShortInfoDto> shortInfoDtoList = new ArrayList<>();
        for (MessageShortInfoDto dto : messageStack.getShortInfoStack()) {
            if (dto.getDateTime() > timestamp) {
                shortInfoDtoList.add(dto);
            } else {
                break;
            }
        }
        return shortInfoDtoList;
    }

    public String getXmlMessageByUuid(String uuid) throws IOException {

        String fileName = messageStack.getFilenameByUuid(uuid);

        Path path = Paths.get(new File(repositoryDir + File.separatorChar + fileName).getAbsolutePath());

        return Files.readString(path, StandardCharsets.UTF_8);
    }

    public void buildCache() {

        File[] msgInRepo = getRepositoryFiles();
        int bottom = maxListDimension > msgInRepo.length ? (msgInRepo.length - 1) : (maxListDimension - 1);
        for (int i = bottom; i >= 0; --i) {
            try {
                String xmlMessage = Files.readString(msgInRepo[i].toPath(), StandardCharsets.UTF_8);
                String fileName = msgInRepo[i].getName();
                Message message = xmlMapper.fromXML(xmlMessage);
                FileNameRepository fileNameRepository = new FileNameRepository(fileName);
                boolean msgIsSent = fileNameRepository.isSent();
                Date timestamp = fileNameRepository.getTimestamp();
                messageStack.add(MessageShortInfoDto.getInstance(message, msgIsSent, timestamp), fileName);

            } catch (IOException | ParseException e) {
                LOGGER.warn("buildCache some problem with file {} : {}", msgInRepo[i], e.getMessage());
            }
        }
    }

    public MessageStack getCache() {
        return this.messageStack;
    }

    private String write(Message message, boolean isSent, Date timestamp) throws IOException {

        FileNameRepository fileNameRepository = new FileNameRepository(message, isSent, timestamp);

        String fileName = fileNameRepository.getFileName();
        String xmlMessage = xmlMapper.toXML(message);
        try (BufferedWriter  writer = new BufferedWriter(new FileWriter(repositoryDir + File.separatorChar + fileName))) {

            writer.write(xmlMessage);
        }

        return fileName;
    }

    public File[] getRepositoryFiles() {

        Path dir = Paths.get(repositoryDir);
        File[] files = dir.toFile().listFiles();
        if (files != null) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        }
        return files;
    }

    static class MessageStack {

        private final Deque<MessageShortInfoDto> shortInfoQueue;
        private final Map<String, String>   uiidFilenameMap;
        private final int maxSize;

        MessageStack(int maxSize) {
            this.maxSize = maxSize;
            this.uiidFilenameMap = new ConcurrentHashMap<>();
            this.shortInfoQueue = new ConcurrentLinkedDeque<>();
        }

        public void add(MessageShortInfoDto dto, String fileName) {

            if (shortInfoQueue.size() >= maxSize) {
                MessageShortInfoDto dtoRemoved = shortInfoQueue.removeLast();
                uiidFilenameMap.remove(dtoRemoved.getId());
            }

            shortInfoQueue.addFirst(dto);
            uiidFilenameMap.put(dto.getId(), fileName);
        }

        public Queue<MessageShortInfoDto> getShortInfoStack() {
            return shortInfoQueue;
        }

        public String getFilenameByUuid(String uuid) {
            return uiidFilenameMap.get(uuid);
        }
    }


    static class FileNameRepository {

        private static final String TIMESTAMP_FORMAT = "yyyyMMdd-hhmmss";
        private static final String ITEM_SEPARATOR = "@";

        private static final String MSG_SENT = "SENT";
        private static final String MSG_RECV = "RECV";

        private final String uuid;
        private final boolean isSent;
        private final String messageTypeName;
        private final Date timestamp;

        private final String fileName;

        FileNameRepository(String fileName) throws ParseException {

            String[] msgElement = fileName.split(ITEM_SEPARATOR);

            SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT);

            this.timestamp = formatter.parse(msgElement[0]);
            this.messageTypeName = msgElement[1];
            this.isSent = msgElement[2].equals(MSG_SENT);
            this.uuid = msgElement[3];
            this.fileName = fileName;
        }


        FileNameRepository(Message message, boolean isSent, Date timestamp) {

            this.uuid = message.getMessageID();

            String direction = isSent ? MSG_SENT : MSG_RECV;
            this.isSent = isSent;

            MessageTypeEnum messageType = MessageTypeEnum.valueOf(message);
            this.messageTypeName = messageType.name();

            this.timestamp = timestamp;

            SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
            String  dateTime = formatter.format(timestamp);

            this.fileName =   dateTime + ITEM_SEPARATOR
                            + messageTypeName + ITEM_SEPARATOR
                            + direction + ITEM_SEPARATOR
                            + uuid;
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
