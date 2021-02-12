/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.cise.sim.api.messages.dto.incident;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * http://emsa.europa.eu/cise-documentation/cise-data-model-1.5.3/model/Incident.html
 *
 * Incident Type:
 *  - MaritimeSafetyIncident
 *  - PollutionIncident
 *  - IrregularMigrationIncident
 *  - LawInfringementIncident
 *  - CrisisIncident
 *
 * Incident SubType:
 *  - CrisisIncident -> CrisisIncidentType
 *  - IrregularMigrationIncident -> IrregularMigrationIncidentType
 *  - LawInfringementIncident -> LawInfringementIncidentType
 *  - MaritimeSafetyIncident -> MaritimeSafetyIncidentType
 *  - PollutionIncident -> PollutionType (Quantity ?)
 *
 * Position of the incident:
 * - Location.Geometry :Latitude, Longitude
 *
 * Vessel (List of):
 * - Vessel.IMONumber,
 * - Vessel.MMSI,
 * - Vessel.VesselType (enumeration)
 * - ObjectEvent.ObjectRole.ObjectRoleInEventType (enumeration)
 *
 * Content
 * - AttachedDocument (abstract class)
 */
public class IncidentRequestDto implements Serializable {

    /**
     * {
     * "incident":{"incidentType":"maritime","subType":"VTSRulesInfringement","latitude":"12","longitude":"23"},
     * "vesselList":[{"vesselType":"PassengerShip","role":"Participant","imoNumber":"1","mmsi":"2"}],
     * "contentList":[]
     * }
     */

    private static final long serialVersionUID = 42L;

    private IncidentInfoDto incident;
    private List<VesselInfoDto> vesselList;
    private List<ContentInfoDto> contentList; //  Base 64 binary document

    public IncidentRequestDto() {
        incident = new IncidentInfoDto();
        vesselList = new ArrayList<>();
        contentList = new ArrayList<>();
    }

    public IncidentInfoDto getIncident() {
        return incident;
    }

    public void setIncident(IncidentInfoDto incident) {
        this.incident = incident;
    }

    public List<VesselInfoDto> getVesselList() {
        return vesselList;
    }

    public void setVesselList(List<VesselInfoDto> vesselList) {
        this.vesselList = vesselList;
    }

    public List<ContentInfoDto> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentInfoDto> contentList) {
        this.contentList = contentList;
    }
}
