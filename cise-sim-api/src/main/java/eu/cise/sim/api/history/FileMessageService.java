package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.api.dto.MessageTypeEnum;
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

/**
 * OCNET-335 Saving messages sent and received
 * The messages sent and received are saved in a file system so they can be easily exported/reused by the user.
 * Clearing the history doesn't delete the messages saved.
 * The messages should be named as follow:
 *
 * date/time (type yyyymmdd-hhmmss)
 * type of message
 * sent/received
 * unique ID to avoid duplicate*
 */
public class FileMessageService implements ThreadMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMessageService.class);

    private final XmlMapper xmlMapper;
    private final String    repositoryDir;

    private final IdTimestampCache idTimestampCache;

    public FileMessageService(XmlMapper xmlMapper, String repositoryDir, int maxMsg) {

        this.xmlMapper          = xmlMapper;
        this.repositoryDir      = new File(repositoryDir).getAbsolutePath();
        this.idTimestampCache   = new IdTimestampCache(maxMsg);

        initCache();
    }

    @Override
    public void messageReceived(Message msgRcv) {

        try {

            Date timestamp = new Date();
            write(msgRcv, Boolean.FALSE, timestamp);
            idTimestampCache.add(msgRcv.getCorrelationID(), timestamp.getTime());

        } catch (IOException e) {
            LOGGER.warn("messageReceived writing error : {}", e.getMessage());
        }

    }

    @Override
    public void messageSent(Message msgSent) {

        try {

            Date timestamp = new Date();
            write(msgSent, Boolean.TRUE, timestamp);
            idTimestampCache.add(msgSent.getCorrelationID(), timestamp.getTime());

        } catch (IOException e) {
            LOGGER.warn("messageSent     writing error : {}", e.getMessage());
        }

    }

    /**
     * Retrieve the list of MessageShortInfoDto instances of the messages that the correlationId was received after the timestamp passed.
     * The list returned is not ordered and contain messages received before the timestamp passed if the correlation id is in a message received after it
     *
     * @param timestamp The inferior time limit of the list
     * @return list of MessageShortInfoDto with correlationId received after the timestamp passed
     */
    @Override
    public List<MessageShortInfoDto> getThreadsAfter(long timestamp) {

        Set<String> correlationIdSet = idTimestampCache.getIdAfter(timestamp);
        List<MessageShortInfoDto> shortInfoDtoList = getMessagesFilterByCorrelationId(correlationIdSet, timestamp);

        if (shortInfoDtoList.size() > 0) {
            LOGGER.info("Retrieve message thread info after timestamp[{}]: found {} messages", new Date(timestamp), shortInfoDtoList.size());
        }

        return shortInfoDtoList;
    }

    /**
     * Retrieve the xml of the cise message, using the uuid of the MessageShortInfoDto associated to it
     *
     * @param uuid the message MessageShortInfoDto uuid
     * @return xml of the message
     * @throws IOException the xml was not found
     */
    @Override
    public ResponseApi<String> getXmlMessageByUuid(String uuid) {

        if (StringUtils.isEmpty(uuid)) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, "getXmlMessageByUuid uuid is null or empty");
        }

        ResponseApi<String> result;
        try {
            Path path =  getPathByUuid(uuid);

            LOGGER.debug("Message Persistence : read xml file -> {}", path.getFileName());
            String xmlMessage = Files.readString(path, StandardCharsets.UTF_8);
            result = new ResponseApi<>(xmlMessage);
        } catch (IOException e) {
            result = new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }

        return result;
    }

    private FileNameRepository write(Message message, boolean isSent, Date timestamp) throws IOException {

        FileNameRepository fileNameRepository = FileNameRepository.getInstance(message, isSent, timestamp);

        String fileName = fileNameRepository.getFileName();
        String xmlMessage = xmlMapper.toXML(message);
        try (BufferedWriter  writer = new BufferedWriter(new FileWriter(repositoryDir + File.separatorChar + fileName))) {

            writer.write(xmlMessage);
        }

        LOGGER.debug("Message Persistence : write xml file -> {}", fileName);

        return fileNameRepository;
    }

    private Path getPathByUuid(String uuid) throws IOException {

        Path dir = Paths.get(repositoryDir);
        File[] files = dir.toFile().listFiles();
        if (files == null) {
            throw new IOException("getUuidFile uuid not found " + uuid);
        }

        for (File f : files) {
            if (!f.getName().endsWith(".xml"))
                continue;
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
     * Build list of MessageShortInfoDto from messages with correlationId contained in the set passed
     *
     * @param correlationIdSet Set of correlation id
     * @return List of MessageShortInfoDto with correlation id contained in correlationIdSet
     */
    private List<MessageShortInfoDto> getMessagesFilterByCorrelationId(Set<String> correlationIdSet, long afterTimestamp) {

        List<MessageShortInfoDto> resultList = new ArrayList<>();

        Path dir = Paths.get(repositoryDir);
        File[] files = dir.toFile().listFiles();

        if (files == null || files.length == 0) {
            return resultList;
        }

        for (File f : files) {
            if (!f.getName().endsWith(".xml"))
                continue;

            try {
                String xmlMessage = Files.readString(f.toPath(), StandardCharsets.UTF_8);
                Message message = xmlMapper.fromXML(xmlMessage);

                if (correlationIdSet.contains(message.getCorrelationID())) {

                    FileNameRepository    fileNameRepository = FileNameRepository.getInstance(f.getName());
                    boolean msgIsSent   = fileNameRepository.isSent();
                    Date timestamp      = fileNameRepository.getTimestamp();
                    String uuid         = fileNameRepository.getUuid();

                    if (timestamp.getTime() > afterTimestamp) {
                        resultList.add(MessageShortInfoDto.getInstance(message, msgIsSent, timestamp, uuid));
                    }
                }

            } catch (IOException | ParseException e) {
                LOGGER.warn("getRepositoryFiles build problem with file {} : {}", f, e.getMessage());
            }
        }

        return resultList;
    }

    private void initCache() {
        Path dir = Paths.get(repositoryDir);
        File[] files = dir.toFile().listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        Map<String, Long> idTimeMap = new HashMap<>();
        for (File f : files) {
            if (!f.getName().endsWith(".xml"))
                continue;
            try {
                String xmlMessage = Files.readString(f.toPath(), StandardCharsets.UTF_8);
                Message message = xmlMapper.fromXML(xmlMessage);
                FileNameRepository    fileNameRepository = FileNameRepository.getInstance(f.getName());
                idTimeMap.put(message.getCorrelationID(), fileNameRepository.getTimestamp().getTime());
            } catch (IOException | ParseException e) {
                LOGGER.warn("initCache build problem with file {} : {}", f, e.getMessage());
            }
        }
        this.idTimestampCache.init(idTimeMap);
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
