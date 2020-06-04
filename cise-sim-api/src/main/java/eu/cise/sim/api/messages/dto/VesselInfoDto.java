package eu.cise.sim.api.messages.dto;

import eu.cise.datamodel.v1.entity.event.ObjectRoleInEventType;
import eu.cise.datamodel.v1.entity.vessel.VesselType;

import java.io.Serializable;

/**
 * Vessel
 * - Vessel.IMONumber,
 * - Vessel.MMSI,
 * - Vessel.VesselType (enumeration)
 * - ObjectEvent.ObjectRole.ObjectRoleInEventType (enumeration)
 *
 */
public class VesselInfoDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private Long imoNumber;
    private Long mmsi;
    private VesselType type;
    private ObjectRoleInEventType role;

    public Long getImoNumber() {
        return imoNumber;
    }

    public void setImoNumber(Long imoNumber) {
        this.imoNumber = imoNumber;
    }

    public Long getMmsi() {
        return mmsi;
    }

    public void setMmsi(Long mmsi) {
        this.mmsi = mmsi;
    }

    public VesselType getType() {
        return type;
    }

    public void setType(VesselType type) {
        this.type = type;
    }

    public ObjectRoleInEventType getRole() {
        return role;
    }

    public void setRole(ObjectRoleInEventType role) {
        this.role = role;
    }
}
