package eu.cise.sim.api.messages.dto.incident;

public enum IncidentTypeEnum {

    /*
    - MaritimeSafetyIncident
 *  - PollutionIncident
 *  - IrregularMigrationIncident
 *  - LawInfringementIncident
 *  - CrisisIncident
     */

    MARITIME_SAFETY("Maritime"),
    POLLUTION("Pollution"),
    IRREGULARITY_MIGRATION("Migration"),
    LAW_INFIRINGEMENT("Law"),
    CRISIS("Crisis");

    private final String guiValue;

    IncidentTypeEnum(String guiValue) {
        this.guiValue = guiValue;
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
}
