package eu.cise.sim.api.messages.builders;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.incident.IrregularMigrationIncident;
import eu.cise.datamodel.v1.entity.incident.IrregularMigrationIncidentType;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;

public class IrregularMigrationBuilder extends AbstractIncidentBuilder {

    @Override
    protected Incident getIncidentInstance(IncidentRequestDto incidentRequestDto) {

        IrregularMigrationIncident incident = new IrregularMigrationIncident();
        incident.setIrregularMigrationIncidentType(IrregularMigrationIncidentType.fromValue(incidentRequestDto.getIncident().getSubType()));

        return incident;
    }
}
