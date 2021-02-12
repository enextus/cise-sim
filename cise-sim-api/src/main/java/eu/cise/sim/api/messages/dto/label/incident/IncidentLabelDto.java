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

package eu.cise.sim.api.messages.dto.label.incident;

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
