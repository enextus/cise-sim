package eu.cise.sim.api.history;


import java.util.List;

public interface HistoryAPI {

    List<MessageShortInfoDto> getLatestMessages();
}
