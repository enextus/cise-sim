package eu.cise.emulator.api.helpers;

import eu.cise.emulator.api.representation.Template;

import java.util.List;

public interface TemplateLoader {
    Template loadTemplate(String templateId);

    List<Template> loadTemplateList();
}
