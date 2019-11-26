package eu.cise.emulator.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.dispatcher.DispatcherType;
import eu.cise.servicemodel.v1.service.Service;

@JsonInclude(Include.NON_NULL)
public class ServiceDetail {

    @JsonProperty("serviceSeaBasin")
    private final String serviceSeaBasin;

    @JsonProperty("serviceType")
    private final String serviceType;

    @JsonProperty("serviceOperation")
    private final String serviceOperation;

    @JsonProperty("serviceRole")
    private final String serviceRole;

    @JsonProperty("serviceID")
    private final String serviceID;

    @JsonProperty("serviceParticipantId")
    private final String serviceParticipantId;

    @JsonProperty("serviceTransportMode")
    private final String serviceTransportMode;

    @JsonProperty("endpointUrl")
    private final String endpointUrl;

    public ServiceDetail(Service service, DispatcherType transportMode, String endpointUrl) {
        this.serviceID = service.getServiceID();
        this.serviceType =
            (service.getServiceType() != null) ? service.getServiceType().value() : null;
        this.serviceOperation =
            (service.getServiceOperation() != null) ? service.getServiceOperation().value() : null;
        this.serviceRole =
            (service.getServiceRole() != null) ? service.getServiceRole().value() : null;
        this.serviceSeaBasin =
            (service.getSeaBasin() != null) ? service.getSeaBasin().value() : null;
        this.serviceParticipantId =
            (service.getParticipant() != null) ? service.getParticipant().getId() : null;
        this.serviceTransportMode = (transportMode
            .toString()); //!= transportMode.REST) ? "SOAP" : "REST"
        this.endpointUrl = endpointUrl;
    }

    public String getServiceParticipantId() {
        return serviceParticipantId;
    }

    public String getServiceSeaBasin() {
        return serviceSeaBasin;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceOperation() {
        return serviceOperation;
    }

    public String getServiceRole() {
        return serviceRole;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getServiceTransportMode() {
        return serviceTransportMode;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }
}
