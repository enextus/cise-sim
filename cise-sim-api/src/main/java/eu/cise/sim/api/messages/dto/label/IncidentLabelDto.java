package eu.cise.sim.api.messages.dto.label;

import eu.cise.datamodel.v1.entity.incident.*;
import eu.cise.sim.api.messages.dto.incident.IncidentTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IncidentLabelDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private final String type;
    private final List<String> subTypeList;


    private IncidentLabelDto(String type, List<String> subTypeList) {
        this.type = type;
        this.subTypeList = subTypeList;
    }

    public static IncidentLabelDto build(IncidentTypeEnum type) {

        switch (type) {
            case MARITIME_SAFETY:
                return buildMaritimeSafetyIncident();

            case POLLUTION:
                return buildPollutionIncident();

            case IRREGULARITY_MIGRATION:
                return buildIrregularMigrationIncident();

            case LAW_INFRINGEMENT:
                return buildLawInfringementIncident();

            case CRISIS:
                return buildCrisisIncident();
        }

        throw new IllegalArgumentException("Incident type is not supported: " + type);
    }

    private static IncidentLabelDto buildMaritimeSafetyIncident() {

        List<String> labelList = new ArrayList<>();

        for (MaritimeSafetyIncidentType type : MaritimeSafetyIncidentType.values()) {
            labelList.add(type.value());
        }
        return new IncidentLabelDto(IncidentTypeEnum.MARITIME_SAFETY.getGuiValue(), labelList);
    }

    private static IncidentLabelDto buildPollutionIncident() {

        List<String> labelList = new ArrayList<>();

        for (PollutionType type : PollutionType.values()) {
            labelList.add(type.value());
        }
        return new IncidentLabelDto(IncidentTypeEnum.POLLUTION.getGuiValue(), labelList);
    }

    private static IncidentLabelDto buildIrregularMigrationIncident() {

        List<String> labelList = new ArrayList<>();

        for (IrregularMigrationIncidentType type : IrregularMigrationIncidentType.values()) {
            labelList.add(type.value());
        }
        return new IncidentLabelDto(IncidentTypeEnum.IRREGULARITY_MIGRATION.getGuiValue(), labelList);
    }

    private static IncidentLabelDto buildLawInfringementIncident() {

        List<String> labelList = new ArrayList<>();

        for (LawInfringementIncidentType type : LawInfringementIncidentType.values()) {
            labelList.add(type.value());
        }
        return new IncidentLabelDto(IncidentTypeEnum.LAW_INFRINGEMENT.getGuiValue(), labelList);
    }

    private static IncidentLabelDto buildCrisisIncident() {

        List<String> labelList = new ArrayList<>();

        for (CrisisIncidentType type : CrisisIncidentType.values()) {
            labelList.add(type.value());
        }
        return new IncidentLabelDto(IncidentTypeEnum.CRISIS.getGuiValue(), labelList);
    }

    public String getType() {
        return type;
    }

    public List<String> getSubTypeList() {
        return subTypeList;
    }
}
