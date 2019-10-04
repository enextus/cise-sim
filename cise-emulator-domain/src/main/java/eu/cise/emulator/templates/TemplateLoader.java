package eu.cise.emulator.templates;

import eu.cise.emulator.exceptions.IOLoaderException;

import java.io.IOException;
import java.util.List;

public interface TemplateLoader {
    Template loadTemplate(String templateId);

    List<Template> loadTemplateList() throws IOLoaderException;
}
