package eu.cise.emulator.templates;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Template implements Serializable {

    private static final long serialVersionUID = 42L;

    private String templateId;

    private String templateContent;

    public String getTemplateName() {
        return templateName;
    }

    private String templateName;


    public Template() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return templateId.equals(template.templateId) &&
                Objects.equals(templateContent, template.templateContent) &&
                templateName.equals(template.templateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateId, templateContent, templateName);
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

    @JsonProperty("templateId")
    public String getTemplateId() {
        return templateId;
    }

    public String getTemplateContent() {
        return templateContent;
    }

}
