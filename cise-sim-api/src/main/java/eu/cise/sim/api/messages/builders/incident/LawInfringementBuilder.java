package eu.cise.sim.api.messages.builders.incident;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.incident.LawInfringementIncident;
import eu.cise.datamodel.v1.entity.incident.LawInfringementIncidentType;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;

public class LawInfringementBuilder extends AbstractIncidentBuilder {

    @Override
    protected Incident getIncidentInstance(IncidentRequestDto incidentRequestDto) {

        LawInfringementIncident incident = new LawInfringementIncident();
        incident.setLawInfringementIncidentType(LawInfringementIncidentType.fromValue(incidentRequestDto.getIncident().getSubType()));

        return incident;
    }
}
