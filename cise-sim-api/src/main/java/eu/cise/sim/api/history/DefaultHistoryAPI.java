package eu.cise.sim.api.history;

import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.io.MessageStorage;

import java.util.ArrayList;
import java.util.List;

public class DefaultHistoryAPI implements HistoryAPI {

    private final MessageStorage<MessageShortInfoDto> messageStorage;

    public DefaultHistoryAPI(MessageStorage<MessageShortInfoDto> messageStorage) {
        this.messageStorage = messageStorage;
    }

    @Override
    public List<MessageShortInfoDto> getLatestMessages() {

        List<MessageShortInfoDto> messageShortInfoDtoList = new ArrayList<>();

        MessageShortInfoDto messageShortInfoDto;
        while ((messageShortInfoDto = messageStorage.read()) != null) {
            if (messageStorage.delete(messageShortInfoDto)) {
                messageShortInfoDtoList.add(messageShortInfoDto);
            }
        }
        return  messageShortInfoDtoList;
    }
}
