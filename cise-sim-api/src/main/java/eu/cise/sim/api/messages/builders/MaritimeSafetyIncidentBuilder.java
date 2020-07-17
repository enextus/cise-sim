package eu.cise.sim.api.messages.builders;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.incident.MaritimeSafetyIncident;
import eu.cise.datamodel.v1.entity.incident.MaritimeSafetyIncidentType;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;

public class MaritimeSafetyIncidentBuilder extends AbstractIncidentBuilder {

    @Override
    protected Incident getIncidentInstance(IncidentRequestDto incidentRequestDto) {

        MaritimeSafetyIncident incident = new MaritimeSafetyIncident();
        incident.setMaritimeSafetyIncidentType(MaritimeSafetyIncidentType.fromValue(incidentRequestDto.getIncident().getSubType()));

        return incident;
    }
}
