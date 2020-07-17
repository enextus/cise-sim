package eu.cise.sim.api.messages.dto.incident;


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

    private String vesselType;
    private String role;
    private String imoNumber;
    private String mmsi;


    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImoNumber() {
        return imoNumber;
    }

    public void setImoNumber(String imoNumber) {
        this.imoNumber = imoNumber;
    }

    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }
}
