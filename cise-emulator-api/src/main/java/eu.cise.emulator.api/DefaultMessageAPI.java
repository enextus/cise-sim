package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMessageAPI implements MessageAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);

    public DefaultMessageAPI(MessageProcessor messageProcessor) {
        LOGGER.info("DefaultMessageAPI");
    }

    @Override
    public void send(JsonNode json) {
        LOGGER.debug("passed through");
    }
}
