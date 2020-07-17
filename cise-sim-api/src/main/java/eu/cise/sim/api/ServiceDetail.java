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

    @JsonProperty("messageHistoryMaxLength")
    private final int maxHistoryMsg;

    @JsonProperty("hideIncidentButton")
    private final boolean hideIncident;


    public ServiceDetail(Service service,
                         DispatcherType transportMode,
                         String endpointUrl,
                         String appVersion,
                         int maxHistoryMsg, boolean hideIncident) {
        this.serviceParticipantId =
            (service.getParticipant() != null) ? service.getParticipant().getId() : null;
        this.serviceTransportMode = transportMode.toString();
        this.endpointUrl = endpointUrl;
        this.appVersion = appVersion;
        this.maxHistoryMsg = maxHistoryMsg;
        this.hideIncident = hideIncident;
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

    public int getMaxHistoryMsg() {
        return maxHistoryMsg;
    }

    public boolean isHideIncident() {
        return hideIncident;
    }
}
