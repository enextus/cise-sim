package eu.cise.emulator.deprecated.web.app.transport;

import com.codahale.metrics.health.HealthCheck;

public class OutBoundWebSocketCheck extends HealthCheck {

    private final String version;

    public OutBoundWebSocketCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
        return Result.unhealthy("unable to check websocket endpoint ");
    }
}
