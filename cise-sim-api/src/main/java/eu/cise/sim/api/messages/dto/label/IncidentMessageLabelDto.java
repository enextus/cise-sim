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
