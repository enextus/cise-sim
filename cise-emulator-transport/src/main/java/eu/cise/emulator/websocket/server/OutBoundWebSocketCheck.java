package eu.cise.emulator.websocket.server;
import com.codahale.metrics.health.HealthCheck;

public class OutBoundWebSocketCheck extends HealthCheck {

    private final String Version;
    public OutBoundWebSocketCheck(String version ) {
        this.Version=version;
    }

    @Override
    protected Result check() throws Exception {

        return Result.unhealthy("unable to check websocket endpoint ");
    }
}
