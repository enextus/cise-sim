package eu.cise.sim.api.messages.builders.incident;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.incident.PollutionIncident;
import eu.cise.datamodel.v1.entity.incident.PollutionType;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;

public class PollutiontBuilder extends AbstractIncidentBuilder {

    @Override
    protected Incident getIncidentInstance(IncidentRequestDto incidentRequestDto) {

        PollutionIncident incident = new PollutionIncident();
        incident.setPollutionType(PollutionType.fromValue(incidentRequestDto.getIncident().getSubType()));

        return incident;
    }
}
