package eu.cise.emulator.templates;

import eu.cise.emulator.exceptions.IOLoaderException;

import java.util.ArrayList;
import java.util.List;

import static eu.eucise.helpers.PushBuilder.newPush;

public class DefaultTemplateLoader implements TemplateLoader {

    @Override
    public Template loadTemplate(String templateId) {
        Template template = new Template(templateId);
        template.setTemplateContent(newPush().id("messageId").correlationId("correlationId").build());
        return template;
    }

    @Override
    public List<Template> loadTemplateList() throws IOLoaderException {
        return new ArrayList<Template>();
    }
}
