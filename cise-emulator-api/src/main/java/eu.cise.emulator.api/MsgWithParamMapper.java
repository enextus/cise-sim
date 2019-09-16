package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.SendParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgWithParamMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgWithParamMapper.class);

    public SendParam extractSendParams(JsonNode any) {
        LOGGER.debug("is called");
        return null;
    }
}
