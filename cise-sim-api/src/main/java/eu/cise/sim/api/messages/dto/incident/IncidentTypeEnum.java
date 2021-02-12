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

import eu.cise.sim.api.messages.builders.IncidentBuilder;
import eu.cise.sim.api.messages.builders.incident.*;

public enum IncidentTypeEnum {

    /*
    - MaritimeSafetyIncident
 *  - PollutionIncident
 *  - IrregularMigrationIncident
 *  - LawInfringementIncident
 *  - CrisisIncident
     */

    MARITIME_SAFETY("Maritime Safety", new MaritimeSafetyIncidentBuilder()),
    POLLUTION("Pollution", new PollutiontBuilder()),
    IRREGULARITY_MIGRATION("Migration", new IrregularMigrationBuilder()),
    LAW_INFRINGEMENT("Law", new LawInfringementBuilder()),
    CRISIS("Crisis", new CrisisIncidentBuilder());

    private final String guiValue;
    private final IncidentBuilder incidentBuilder;

    IncidentTypeEnum(String guiValue, IncidentBuilder incidentBuilder) {
        this.guiValue = guiValue;
        this.incidentBuilder = incidentBuilder;
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

    public IncidentBuilder getIncidentBuilder() {
        return incidentBuilder;
    }
}
