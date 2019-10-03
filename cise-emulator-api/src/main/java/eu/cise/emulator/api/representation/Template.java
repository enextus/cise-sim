package eu.cise.emulator.api.representation;

import java.io.Serializable;
import java.util.Objects;

public class Template implements Serializable {

    private static final long serialVersionUID = 42L;

    private String templateId;

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
}
