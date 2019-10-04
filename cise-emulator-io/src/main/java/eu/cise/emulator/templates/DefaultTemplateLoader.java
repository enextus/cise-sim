package eu.cise.emulator.templates;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.exceptions.IOLoaderDirectoryNotFoundException;
import eu.cise.emulator.exceptions.IOLoaderException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static eu.eucise.helpers.PushBuilder.newPush;

public class DefaultTemplateLoader implements TemplateLoader {

    private final EmuConfig emuConfig;

    public DefaultTemplateLoader(EmuConfig emuConfig) {
        this.emuConfig = emuConfig;

    }

    @Override
    public Template loadTemplate(String templateId) {
        Template template = new Template(templateId);
        template.setTemplateContent(newPush().id("messageId").correlationId("correlationId").build());
        return template;
    }

    @Override
    public List<Template> loadTemplateList() throws IOLoaderException {
        List<Template> filesList = new ArrayList<>();
        try {
            File folder = new File(emuConfig.templateMessagesDirectory());
            Files.list(Paths.get(folder.getAbsolutePath()))
                    .filter(s -> s.toString().endsWith(".xml"))
                    .sorted()
                    .forEach(e -> filesList.add(new Template(e.toFile().getName())));
        } catch (IOException e) {
            if (e instanceof java.nio.file.NoSuchFileException) {
                throw new IOLoaderDirectoryNotFoundException(e);
            }
            throw new IOLoaderException(e);
        }
        return filesList;
    }
}
