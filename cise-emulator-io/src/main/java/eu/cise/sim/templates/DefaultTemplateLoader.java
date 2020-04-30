package eu.cise.sim.templates;


import eu.cise.sim.engine.SimConfig;
import eu.cise.sim.exceptions.DirectoryNotFoundEx;
import eu.cise.sim.exceptions.LoaderEx;
import eu.cise.sim.exceptions.TemplateNotFoundEx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DefaultTemplateLoader implements TemplateLoader {

    private final SimConfig simConfig;

    public DefaultTemplateLoader(SimConfig simConfig) {
        this.simConfig = simConfig;
    }

    @Override
    public Template loadTemplate(String templateId) {
        return new Template(templateId, templateId, readFile(templateId));
    }

    @Override
    public List<Template> loadTemplateList() throws LoaderEx {
        try {
            return Files.list(messageTemplatePath(simConfig.messageTemplateDir()))
                    .filter(s -> s.toString().endsWith(".xml"))
                    .sorted()
                    .map(e -> e.toFile().getName())
                    .map(e -> new Template(e, e))
                    .collect(Collectors.toList());

        } catch (NoSuchFileException e) {
            throw new DirectoryNotFoundEx(e);
        } catch (IOException e) {
            throw new LoaderEx(e);
        }
    }

    // TODO add the support to read a file also if the path is absolute without
    //  using the emuConfig.messageTemplateDir
    private String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(getFilePath(fileName)), UTF_8);
        } catch (IOException e) {
            throw new TemplateNotFoundEx(fileName);
        }
    }

    private Path getFilePath(String fileName) {
        return Paths.get(simConfig.messageTemplateDir() + "/" + fileName);
    }

    private Path messageTemplatePath(String pathname) {
        return Paths.get(new File(pathname).getAbsolutePath());
    }
}
