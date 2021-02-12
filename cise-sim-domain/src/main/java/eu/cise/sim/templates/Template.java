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

package eu.cise.sim.templates;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

public class Template implements Serializable {

    private static final long serialVersionUID = 42L;

    private String templateId;

    private String templateContent;
    private String templateName;

    public Template() {
    }

    public Template(String templateId, String templateName) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateContent = null;
    }

    public Template(String templateId, String templateName, String templateContent) {
        this.templateId = templateId;
        this.templateContent = templateContent;
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    @JsonProperty("templateId")
    public String getTemplateId() {
        return templateId;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Template template = (Template) o;
        return templateId.equals(template.templateId) &&
            Objects.equals(templateContent, template.templateContent) &&
            templateName.equals(template.templateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateId, templateContent, templateName);
    }

    @Override
    public String toString() {
        return "Template{" +
            "templateId='" + templateId + '\'' +
            ", templateName='" + templateName + '\'' +
            '}';
    }
}
