package eu.cise.sim.api.messages.dto;

import java.io.Serializable;
import java.util.List;

/**
 * http://emsa.europa.eu/cise-documentation/cise-data-model-1.5.3/model/Incident.html
 *
 * Incident Type:
 *  - MaritimeSafetyIncident
 *  - PollutionIncident
 *  - IrregularMigrationIncident
 *  - LawInfringementIncident
 *  - CrisisIncident
 *
 * Incident SubType:
 *  - CrisisIncident -> CrisisIncidentType
 *  - IrregularMigrationIncident -> IrregularMigrationIncidentType
 *  - LawInfringementIncident -> LawInfringementIncidentType
 *  - MaritimeSafetyIncident -> MaritimeSafetyIncidentType
 *  - PollutionIncident -> PollutionType (Quantity ?)
 *
 * Position of the incident:
 * - Location.Geometry :Latitude, Longitude
 *
 * Vessel (List of):
 * - Vessel.IMONumber,
 * - Vessel.MMSI,
 * - Vessel.VesselType (enumeration)
 * - ObjectEvent.ObjectRole.ObjectRoleInEventType (enumeration)
 *
 * todo Add extra documents (List Of)
 */
public class IncidentDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private String type;
    private String subType;
    private String latitude;
    private String longitude;
    private List<VesselInfoDto> vesselList;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<VesselInfoDto> getVesselList() {
        return vesselList;
    }

    public void setVesselList(List<VesselInfoDto> vesselList) {
        this.vesselList = vesselList;
    }
}
