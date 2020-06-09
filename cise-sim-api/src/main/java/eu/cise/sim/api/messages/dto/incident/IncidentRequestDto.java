package eu.cise.sim.api.messages.dto.incident;

import java.io.Serializable;
import java.util.ArrayList;
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
 * Content
 * - AttachedDocument (abstract class)
 */
public class IncidentRequestDto implements Serializable {

    /**
     * {
     * "incident":{"incidentType":"maritime","subType":"VTSRulesInfringement","latitude":"12","longitude":"23"},
     * "vesselList":[{"vesselType":"PassengerShip","role":"Participant","imoNumber":"1","mmsi":"2"}],
     * "contentList":[]
     * }
     */

    private static final long serialVersionUID = 42L;

    private IncidentInfoDto incident;
    private List<VesselInfoDto> vesselList;
    private List<ContentInfoDto> contentList; //  Base 64 binary document

    public IncidentRequestDto() {
        incident = new IncidentInfoDto();
        vesselList = new ArrayList<>();
        contentList = new ArrayList<>();
    }

    public IncidentInfoDto getIncident() {
        return incident;
    }

    public void setIncident(IncidentInfoDto incident) {
        this.incident = incident;
    }

    public List<VesselInfoDto> getVesselList() {
        return vesselList;
    }

    public void setVesselList(List<VesselInfoDto> vesselList) {
        this.vesselList = vesselList;
    }

    public List<ContentInfoDto> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentInfoDto> contentList) {
        this.contentList = contentList;
    }
}
