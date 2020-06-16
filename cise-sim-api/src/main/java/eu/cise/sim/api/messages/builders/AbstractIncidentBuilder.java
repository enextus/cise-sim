package eu.cise.sim.api.messages.builders;

import eu.cise.datamodel.v1.entity.document.VesselDocument;
import eu.cise.datamodel.v1.entity.event.Event;
import eu.cise.datamodel.v1.entity.event.ObjectRoleInEventType;
import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.location.Geometry;
import eu.cise.datamodel.v1.entity.location.Location;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.datamodel.v1.entity.vessel.VesselType;
import eu.cise.sim.api.messages.dto.incident.ContentInfoDto;
import eu.cise.sim.api.messages.dto.incident.IncidentInfoDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.VesselInfoDto;

import java.util.Base64;
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
 **/
public abstract class AbstractIncidentBuilder implements IncidentBuilder {

    @Override
    public final Incident build(IncidentRequestDto incidentRequestDto) {

        Incident msg =  getIncidentInstance(incidentRequestDto);

        IncidentInfoDto incidentInfo = incidentRequestDto.getIncident();

        Geometry geometry = new Geometry();
        geometry.setLatitude(incidentInfo.getLatitude());
        geometry.setLongitude(incidentInfo.getLongitude());

        Location location = new Location();
        location.getGeometries().add(geometry);

        Event.LocationRel locationRel = new Event.LocationRel();
        locationRel.setLocation(location);

        msg.getLocationRels().add(locationRel);

        List<VesselInfoDto> vesselInfoDtoList = incidentRequestDto.getVesselList();
        for (VesselInfoDto vesselInfo : vesselInfoDtoList) {
            Vessel vessel = new Vessel();
            vessel.setIMONumber(Long.valueOf(vesselInfo.getImoNumber()));
            vessel.setMMSI(Long.valueOf(vesselInfo.getMmsi()));
            vessel.getShipTypes().add(VesselType.fromValue(vesselInfo.getVesselType()));

            Event.InvolvedObjectRel involvedObjectRel = new Event.InvolvedObjectRel();
            involvedObjectRel.setObject(vessel);
            involvedObjectRel.setObjectRole(ObjectRoleInEventType.fromValue(vesselInfo.getRole()));

            msg.getInvolvedObjectRels().add(involvedObjectRel);

        }

        List<ContentInfoDto> contentList = incidentRequestDto.getContentList();
        for (ContentInfoDto contentInfoDto : contentList) {
            VesselDocument document = new VesselDocument();
            document.setContent(Base64.getDecoder().decode(contentInfoDto.getContent())); // NB this method do also the encode 64
            document.setTitle(contentInfoDto.getName());

            /*
            Metadata metadata = new Metadata();
            metadata.setFileMediaType();
            document.getMetadatas()
            */
            Event.DocumentRel documentRel = new Event.DocumentRel();
            documentRel.setDocument(document);
            msg.getDocumentRels().add(documentRel);
        }

        return msg;
    }

    protected abstract Incident getIncidentInstance(IncidentRequestDto incidentRequestDto);
}
