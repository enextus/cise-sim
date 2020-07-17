package eu.cise.sim.api.history;


import eu.cise.sim.api.dto.MessageShortInfoDto;

import java.util.List;

public interface HistoryAPI {

    List<MessageShortInfoDto> getThreadsAfter(long timestamp);

    String getXmlMessageByUuid(String uuid);
}
