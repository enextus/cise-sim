package eu.cise.sim.api.messages.dto.discovery;

import java.io.Serializable;

public class DiscoveryRequestDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private String country;
    private String seaBasin;
    private String serviceType;
    private String serviceOperation;
    private String serviceRole;

    private String discoverySender;
    private String discoveryServiceType;
    private String discoveryServiceOperation;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSeaBasin() {
        return seaBasin;
    }

    public void setSeaBasin(String seaBasin) {
        this.seaBasin = seaBasin;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(String serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public String getServiceRole() {
        return serviceRole;
    }

    public void setServiceRole(String serviceRole) {
        this.serviceRole = serviceRole;
    }

    public String getDiscoverySender() {
        return discoverySender;
    }

    public void setDiscoverySender(String discoverySender) {
        this.discoverySender = discoverySender;
    }

    public String getDiscoveryServiceType() {
        return discoveryServiceType;
    }

    public void setDiscoveryServiceType(String discoveryServiceType) {
        this.discoveryServiceType = discoveryServiceType;
    }

    public String getDiscoveryServiceOperation() {
        return discoveryServiceOperation;
    }

    public void setDiscoveryServiceOperation(String discoveryServiceOperation) {
        this.discoveryServiceOperation = discoveryServiceOperation;
    }

    @Override
    public String toString() {
        return "Country [" + this.country
                + "] Sea Basin [" + this.seaBasin
                + "] Service Type [" + this.serviceType
                + "] Service Operation [" + this.serviceOperation
                + "] Service Role [" + this.serviceRole
                + "] Discovery Consumer [" + this.discoverySender
                + "] Discovery ServiceType [" + this.discoveryServiceType
                + "]";
    }
}
