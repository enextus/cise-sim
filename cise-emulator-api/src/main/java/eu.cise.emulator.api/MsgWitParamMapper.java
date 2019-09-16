package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgWitParamMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgWitParamMapper.class);

    public void map(JsonNode any) {
        LOGGER.debug("is called");
    }
}
