package eu.cise.sim.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.sim.engine.DispatcherType;

@JsonInclude(Include.NON_NULL)
public class ServiceDetail {

    @JsonProperty("serviceParticipantId")
    private final String serviceParticipantId;

    @JsonProperty("serviceTransportMode")
    private final String serviceTransportMode;

    @JsonProperty("endpointUrl")
    private final String endpointUrl;

    @JsonProperty("appVersion")
    private final String appVersion;

    public ServiceDetail(Service service,
        DispatcherType transportMode,
        String endpointUrl,
        String appVersion) {
        this.serviceParticipantId =
            (service.getParticipant() != null) ? service.getParticipant().getId() : null;
        this.serviceTransportMode = transportMode.toString();
        this.endpointUrl = endpointUrl;
        this.appVersion = appVersion;
    }

    public String getServiceParticipantId() {
        return serviceParticipantId;
    }

    public String getServiceTransportMode() {
        return serviceTransportMode;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }
}
