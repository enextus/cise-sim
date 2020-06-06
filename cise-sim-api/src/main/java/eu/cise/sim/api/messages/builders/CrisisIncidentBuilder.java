package eu.cise.sim.api.messages.builders;

import eu.cise.datamodel.v1.entity.incident.CrisisIncident;
import eu.cise.datamodel.v1.entity.incident.CrisisIncidentType;
import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;

public class CrisisIncidentBuilder extends AbstractIncidentBuilder {

    @Override
    protected Incident getIncidentInstance(IncidentRequestDto incidentRequestDto) {

        CrisisIncident incident = new CrisisIncident();
        incident.setCrisisIncidentType(CrisisIncidentType.fromValue(incidentRequestDto.getIncident().getSubType()));

        return incident;
    }
}
