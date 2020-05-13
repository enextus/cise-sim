package eu.cise.sim.api.history;

import eu.cise.sim.api.dto.MessageShortInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class DefaultHistoryAPI implements HistoryAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHistoryAPI.class);

    private final FileMessageRepository fileMessageRepository;

    public DefaultHistoryAPI(FileMessageRepository fileMessageRepository) {
        this.fileMessageRepository = fileMessageRepository;
    }

    public List<MessageShortInfoDto> getLatestMessages(long timestamp) {
        return fileMessageRepository.getShortInfoAfter(timestamp);
    }

    @Override
    public String getXmlMessageByUuid(String uuid) {
        String xmlMessage = null;
        try {
            xmlMessage = fileMessageRepository.getXmlMessageByUuid(uuid);
        } catch (IOException e) {
            LOGGER.warn("getXmlMessageByUuid exception : {}", e.getMessage());
        }
        return xmlMessage;
    }
}
