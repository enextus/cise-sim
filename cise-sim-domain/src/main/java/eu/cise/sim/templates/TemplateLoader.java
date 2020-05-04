package eu.cise.sim.templates;

import eu.cise.sim.exceptions.LoaderEx;

import java.util.List;

public interface TemplateLoader {
    Template loadTemplate(String templateId);

    List<Template> loadTemplateList() throws LoaderEx;
}
