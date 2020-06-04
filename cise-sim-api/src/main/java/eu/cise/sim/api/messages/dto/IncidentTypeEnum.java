package eu.cise.sim.api.messages.dto;

public enum IncidentTypeEnum {

    /*
    - MaritimeSafetyIncident
 *  - PollutionIncident
 *  - IrregularMigrationIncident
 *  - LawInfringementIncident
 *  - CrisisIncident
     */

    MARITIME_SAFETY("maritime"),
    POLLUTION("pollution"),
    IRREGULARITY_MIGRATION("migration"),
    LAW_INFIRINGEMENT("law"),
    CRISIS("crisis");

    private String guiValue;

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
