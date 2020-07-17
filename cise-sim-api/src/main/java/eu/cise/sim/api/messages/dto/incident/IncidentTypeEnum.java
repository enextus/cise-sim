package eu.cise.sim.api.messages.dto.incident;

import eu.cise.sim.api.messages.builders.*;

public enum IncidentTypeEnum {

    /*
    - MaritimeSafetyIncident
 *  - PollutionIncident
 *  - IrregularMigrationIncident
 *  - LawInfringementIncident
 *  - CrisisIncident
     */

    MARITIME_SAFETY("Maritime Safety", new MaritimeSafetyIncidentBuilder()),
    POLLUTION("Pollution", new PollutiontBuilder()),
    IRREGULARITY_MIGRATION("Migration", new IrregularMigrationBuilder()),
    LAW_INFRINGEMENT("Law", new LawInfringementBuilder()),
    CRISIS("Crisis", new CrisisIncidentBuilder());

    private final String guiValue;
    private final IncidentBuilder incidentBuilder;

    IncidentTypeEnum(String guiValue, IncidentBuilder incidentBuilder) {
        this.guiValue = guiValue;
        this.incidentBuilder = incidentBuilder;
    }

    public static IncidentTypeEnum valueOfGuiValue(String guiValue) {

        for (IncidentTypeEnum e : IncidentTypeEnum.values()) {
            if (e.guiValue.equalsIgnoreCase(guiValue)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Incident type is unknown: " + guiValue);
    }

    public String getGuiValue() {
        return guiValue;
    }

    public IncidentBuilder getIncidentBuilder() {
        return incidentBuilder;
    }
}
