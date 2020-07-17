package eu.cise.sim.api.messages.dto.label;

import eu.cise.datamodel.v1.entity.event.ObjectRoleInEventType;
import eu.cise.datamodel.v1.entity.vessel.VesselType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VesselLabelDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private final List<String> typeList;
    private final List<String> roleList;

    private static VesselLabelDto instance;

    static {

        List<String> typeList = new ArrayList<>();
        for (VesselType type : VesselType.values()) {
            typeList.add(type.value());
        }

        List<String> roleList = new ArrayList<>();
        for (ObjectRoleInEventType type : ObjectRoleInEventType.values()) {
            roleList.add(type.value());
        }

        instance = new VesselLabelDto(typeList, roleList);
    }

    private VesselLabelDto(List<String> typeList, List<String> roleList) {
        this.typeList = typeList;
        this.roleList = roleList;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public static VesselLabelDto getInstance() {
        return instance;
    }
}
