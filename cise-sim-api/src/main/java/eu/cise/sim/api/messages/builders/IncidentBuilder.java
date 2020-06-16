package eu.cise.sim.api.messages.builders;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;

public interface IncidentBuilder {

    Incident build(IncidentRequestDto incidentRequestDto);
}
