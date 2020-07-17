package eu.cise.sim.api.messages.dto.discovery;

import java.io.Serializable;

public class DiscoveryRequestDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private String country;
    private String seaBasin;
    private String serviceType;

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

    @Override
    public String toString() {
        return "Country [" + this.country + "] Sea Basin [" + this.seaBasin + "] Service Type [" + this.serviceType + "]";
    }
}
