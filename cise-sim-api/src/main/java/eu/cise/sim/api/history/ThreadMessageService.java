package eu.cise.sim.api.history;

import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.io.MessagePersistence;

import java.io.IOException;
import java.util.List;

public interface ThreadMessageService extends MessagePersistence, ThreadMessageAPI {

    /**
     * Retrieve the list of MessageShortInfoDto instances of the messages that the correlationId was received after the timestamp passed.
     * The list returned is not ordered and contain messages received before the timestamp passed if the correlation id is in a message received after it
     *
     * @param timestamp The inferior time limit of the list
     * @return list of MessageShortInfoDto with correlationId received after the timestamp passed
     */
    List<MessageShortInfoDto> getThreadsAfter(long timestamp);

    /**
     * Retrieve the xml of the cise message, using the uuid of the MessageShortInfoDto associated to it
     *
     * @param uuid the message MessageShortInfoDto uuid
     * @return xml of the message
     * @throws IOException the xml was not found
     */
    ResponseApi<String> getXmlMessageByUuid(String uuid);

}
