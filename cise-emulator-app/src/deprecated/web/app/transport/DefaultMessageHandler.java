package eu.cise.emulator.deprecated.web.app.transport;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMessageHandler implements MessageHandler {

    private final Logger logger = (Logger) LoggerFactory.getLogger(OutBoundWebSocketService.class);

    @Override
    public void handleMessage(String message) {
        logger.debug("captured by " + this.getClass().getCanonicalName() + " :: " + message);
    }
}
