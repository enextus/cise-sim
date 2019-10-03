package eu.cise.emulator.templates;

import eu.cise.servicemodel.v1.message.Message;

import java.io.Serializable;
import java.util.Objects;

public class Template implements Serializable {

    private static final long serialVersionUID = 42L;

    private String templateId;

    private Message templateContent;

    public Template() {
    }

    public Template(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(templateId, template.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateId);
    }

    public Message getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(Message templateContent) {
        this.templateContent = templateContent;
    }
}
