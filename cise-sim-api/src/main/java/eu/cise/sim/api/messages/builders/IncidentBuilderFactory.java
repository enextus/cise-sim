package eu.cise.sim.api.messages.builders;

import eu.cise.sim.api.messages.dto.incident.IncidentTypeEnum;

public class IncidentBuilderFactory {

    public static IncidentBuilderInterface getBuilder(IncidentTypeEnum type) {
        switch (type) {
            case MARITIME_SAFETY:
                return new MaritimeSafetyIncidentBuilder();

            case POLLUTION:
                return new PollutiontBuilder();

            case IRREGULARITY_MIGRATION:
                return new IrregularMigrationBuilder();

            case LAW_INFIRINGEMENT:
                return new LawInfringementBuilder();

            case CRISIS:
                return new CrisisIncidentBuilder();

            default:
                throw new IllegalArgumentException("Incident type is not supported: " + type);
        }
    }
}
