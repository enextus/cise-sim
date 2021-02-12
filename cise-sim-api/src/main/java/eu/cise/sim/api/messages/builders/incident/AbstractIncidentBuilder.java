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

package eu.cise.sim.api.messages.builders.incident;

import eu.cise.datamodel.v1.entity.document.VesselDocument;
import eu.cise.datamodel.v1.entity.event.Event;
import eu.cise.datamodel.v1.entity.event.ObjectRoleInEventType;
import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.location.Geometry;
import eu.cise.datamodel.v1.entity.location.Location;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.datamodel.v1.entity.vessel.VesselType;
import eu.cise.sim.api.messages.builders.IncidentBuilder;
import eu.cise.sim.api.messages.dto.incident.ContentInfoDto;
import eu.cise.sim.api.messages.dto.incident.IncidentInfoDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.VesselInfoDto;

import java.util.Base64;
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
 **/
public abstract class AbstractIncidentBuilder implements IncidentBuilder {

    @Override
    public final Incident build(IncidentRequestDto incidentRequestDto) {

        Incident msg =  getIncidentInstance(incidentRequestDto);

        IncidentInfoDto incidentInfo = incidentRequestDto.getIncident();

        Geometry geometry = new Geometry();
        geometry.setLatitude(incidentInfo.getLatitude());
        geometry.setLongitude(incidentInfo.getLongitude());

        Location location = new Location();
        location.getGeometries().add(geometry);

        Event.LocationRel locationRel = new Event.LocationRel();
        locationRel.setLocation(location);

        msg.getLocationRels().add(locationRel);

        List<VesselInfoDto> vesselInfoDtoList = incidentRequestDto.getVesselList();
        for (VesselInfoDto vesselInfo : vesselInfoDtoList) {
            Vessel vessel = new Vessel();
            vessel.setIMONumber(Long.valueOf(vesselInfo.getImoNumber()));
            vessel.setMMSI(Long.valueOf(vesselInfo.getMmsi()));
            vessel.getShipTypes().add(VesselType.fromValue(vesselInfo.getVesselType()));

            Event.InvolvedObjectRel involvedObjectRel = new Event.InvolvedObjectRel();
            involvedObjectRel.setObject(vessel);
            involvedObjectRel.setObjectRole(ObjectRoleInEventType.fromValue(vesselInfo.getRole()));

            msg.getInvolvedObjectRels().add(involvedObjectRel);

        }

        List<ContentInfoDto> contentList = incidentRequestDto.getContentList();
        for (ContentInfoDto contentInfoDto : contentList) {
            VesselDocument document = new VesselDocument();
            document.setContent(Base64.getDecoder().decode(contentInfoDto.getContent())); // NB this method do also the encode 64
            document.setTitle(contentInfoDto.getName());

            /*
            Metadata metadata = new Metadata();
            metadata.setFileMediaType();
            document.getMetadatas()
            */
            Event.DocumentRel documentRel = new Event.DocumentRel();
            documentRel.setDocument(document);
            msg.getDocumentRels().add(documentRel);
        }

        return msg;
    }

    protected abstract Incident getIncidentInstance(IncidentRequestDto incidentRequestDto);
}
