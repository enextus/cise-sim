package eu.cise.sim.api.messages.dto.incident;

import java.io.Serializable;

public class IncidentInfoDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private String incidentType;
    private String subType;
    private String latitude;
    private String longitude;

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
