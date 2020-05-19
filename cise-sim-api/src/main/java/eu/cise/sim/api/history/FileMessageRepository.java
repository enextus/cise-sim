package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.api.dto.MessageTypeEnum;
import eu.cise.sim.io.MessagePersistence;
import eu.eucise.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
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
    private final int       maxListDimension;

    private final MessageStack messageStack;

    public FileMessageRepository(XmlMapper xmlMapper, String repositoryDir) {
        this(xmlMapper, repositoryDir, 10);
    }

    public FileMessageRepository(XmlMapper xmlMapper, String repositoryDir, int maxMsg) {
        this.xmlMapper = xmlMapper;
        this.repositoryDir = new File(repositoryDir).getAbsolutePath();
        this.maxListDimension = maxMsg;

        this.messageStack = MessageStack.build(this.repositoryDir, this.maxListDimension, this.xmlMapper);
    }

    @Override
    public void messageReceived(Message msgRcv) {

        try {

            Date timestamp = new Date();
            FileNameRepository fileNameRepo = write(msgRcv, Boolean.FALSE, timestamp);

            messageStack.add(MessageShortInfoDto.getInstance(msgRcv, MSG_RECV, timestamp, fileNameRepo.getUuid()), fileNameRepo.getFileName());

        } catch (IOException e) {
            LOGGER.warn("messageReceived writing error : {}", e.getMessage());
        }

    }

    @Override
    public void messageSent(Message msgSent) {

        try {

            Date timestamp = new Date();
            FileNameRepository fileNameRepo = write(msgSent, Boolean.TRUE, timestamp);

            messageStack.add(MessageShortInfoDto.getInstance(msgSent, MSG_SENT, timestamp, fileNameRepo.getUuid()), fileNameRepo.getFileName());

        } catch (IOException e) {
            LOGGER.warn("messageSent     writing error : {}", e.getMessage());
        }

    }

    /**
     * Retrieve the list of MessageShortInfoDto instances of the messages stored after the timestamp passed.
     * The list returned is ordered by time descending
     * NB: The max number of elements is equal to the maxListDimension parameter
     *
     * @param timestamp The inferior time limit of the list
     * @return list of MessageShortInfoDto with timestamp duperior of the on passed
     */
    public List<MessageShortInfoDto> getShortInfoAfter(long timestamp) {

        List<MessageShortInfoDto> shortInfoDtoList = new ArrayList<>();
        for (MessageShortInfoDto dto : messageStack.getShortInfoStack()) {
            if (dto.getDateTime() > timestamp) {
                shortInfoDtoList.add(dto);
            } else {
                break;
            }
        }

        LOGGER.info("getShortInfoAfter timestamp[{}} number of messages[{}]", new Date(timestamp), shortInfoDtoList.size());

        return shortInfoDtoList;
    }

    /**
     * Retrieve the xml of the cise message, using his uuid
     * First will be checked the local cache. If it isn't found, the file system will be checked
     *
     * @param uuid the message uuid
     * @return xml if the message
     * @throws IOException the xml was not found
     */
    public String getXmlMessageByUuid(String uuid) throws IOException {

        if (StringUtils.isEmpty(uuid)) {
            throw new IOException("getXmlMessageByUuid uuid is null or empty");
        }

        String fileName = messageStack.getFilenameByUuid(uuid);

        Path path = fileName == null ?
                getPathByUuid(uuid) :
                Paths.get(new File(repositoryDir + File.separatorChar + fileName).getAbsolutePath());

        LOGGER.info("getXmlMessageByUuid uuid[{}] -> {}", uuid, path.getFileName());

        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private FileNameRepository write(Message message, boolean isSent, Date timestamp) throws IOException {

        FileNameRepository fileNameRepository = FileNameRepository.getInstance(message, isSent, timestamp);

        String fileName = fileNameRepository.getFileName();
        String xmlMessage = xmlMapper.toXML(message);
        try (BufferedWriter  writer = new BufferedWriter(new FileWriter(repositoryDir + File.separatorChar + fileName))) {

            writer.write(xmlMessage);
        }

        LOGGER.info("write xml file {}", fileName);

        return fileNameRepository;
    }

    private Path getPathByUuid(String uuid) throws IOException {


        Path dir = Paths.get(repositoryDir);
        File[] files = dir.toFile().listFiles();
        if (files == null) {
            throw new IOException("getUuidFile uuid not found " + uuid);
        }

        for (File f : files) {
            try {
                FileNameRepository fileNameRepository = FileNameRepository.getInstance(f.getName());
                if (fileNameRepository.getUuid().equals(uuid)) {
                    return f.toPath();
                }
            } catch (ParseException ignored) { }
        }

        throw new IOException("getUuidFile uuid not found " + uuid);
    }



    /**
     * Convenient Cache class
     */
    static class MessageStack {

        private final Deque<MessageShortInfoDto> shortInfoQueue;
        private final Map<String, String>        uiidFilenameMap;
        private final int maxSize;

        private MessageStack(int maxSize) {
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

        public void clear() {
            uiidFilenameMap.clear();
            shortInfoQueue.clear();
        }

        public int size() {
            return shortInfoQueue.size();
        }

        public static MessageStack build(String repositoryDir, int maxSize, XmlMapper xmlMapper) {

            MessageStack messageStack = new MessageStack(maxSize);

            File[] msgInRepo = getRepositoryFiles(repositoryDir);
            if (msgInRepo == null) {
                LOGGER.info("MessageStack build with size 0");
                return messageStack;
            }

            int bottom = maxSize > msgInRepo.length ? (msgInRepo.length - 1) : (maxSize - 1);
            for (int i = bottom; i >= 0; --i) {
                try {
                    String xmlMessage = Files.readString(msgInRepo[i].toPath(), StandardCharsets.UTF_8);
                    Message message = xmlMapper.fromXML(xmlMessage);

                    String fileName = msgInRepo[i].getName();
                    FileNameRepository    fileNameRepository = FileNameRepository.getInstance(fileName);
                    boolean msgIsSent   = fileNameRepository.isSent();
                    Date timestamp      = fileNameRepository.getTimestamp();
                    messageStack.add(MessageShortInfoDto.getInstance(message, msgIsSent, timestamp, fileNameRepository.getUuid()), fileName);

                } catch (IOException | ParseException e) {
                    LOGGER.warn("MessageStack build problem with file {} : {}", msgInRepo[i], e.getMessage());
                }
            }

            LOGGER.info("MessageStack build with size {}", messageStack.size());

            return messageStack;
        }

        /**
         * Retrieve all the File on the repository directory, ordered by last modified parameter descending
         * @return Array of all the files in the repository directory
         */
        private static File[] getRepositoryFiles(String repositoryDir) {

            Path dir = Paths.get(repositoryDir);
            File[] files = dir.toFile().listFiles();
            if (files != null) {
                Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            }
            return files;
        }
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
            String  dateTime = formatter.format(timestamp);

            return    dateTime + ITEM_SEPARATOR
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
