package eu.cise.emulator.templates;

import java.util.List;

public interface TemplateLoader {
    Template loadTemplate(String templateId);

    List<Template> loadTemplateList();
}
