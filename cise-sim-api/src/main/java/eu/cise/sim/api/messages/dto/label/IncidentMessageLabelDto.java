package eu.cise.sim.api.messages.dto.label;

import eu.cise.sim.api.messages.dto.incident.IncidentTypeEnum;
import eu.cise.sim.api.messages.dto.label.incident.IncidentLabelDto;
import eu.cise.sim.api.messages.dto.label.incident.VesselLabelDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IncidentMessageLabelDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private final List<IncidentLabelDto>  incidentList;
    private final VesselLabelDto vessel;

    private static IncidentMessageLabelDto instance;

    static {

        List<IncidentLabelDto>  incidentList = new ArrayList<>();
        incidentList.add(IncidentLabelDto.build(IncidentTypeEnum.MARITIME_SAFETY));
        incidentList.add(IncidentLabelDto.build(IncidentTypeEnum.CRISIS));
        incidentList.add(IncidentLabelDto.build(IncidentTypeEnum.IRREGULARITY_MIGRATION));
        incidentList.add(IncidentLabelDto.build(IncidentTypeEnum.LAW_INFRINGEMENT));
        incidentList.add(IncidentLabelDto.build(IncidentTypeEnum.POLLUTION));

        instance = new IncidentMessageLabelDto(incidentList, VesselLabelDto.getInstance());
    }

    private IncidentMessageLabelDto(List<IncidentLabelDto> incidentList, VesselLabelDto vessel) {
        this.incidentList = incidentList;
        this.vessel = vessel;
    }

    public List<IncidentLabelDto> getIncidentList() {
        return incidentList;
    }

    public VesselLabelDto getVessel() {
        return vessel;
    }

    public static IncidentMessageLabelDto getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        IncidentMessageLabelDto labels =  IncidentMessageLabelDto.getInstance();
        System.out.println(labels);
    }
}
