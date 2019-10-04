package eu.cise.emulator.templates;

import static eu.eucise.helpers.PushBuilder.newPush;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.exceptions.DirectoryNotFoundEx;
import eu.cise.emulator.exceptions.LoaderEx;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DefaultTemplateLoader implements TemplateLoader {

    private final EmuConfig emuConfig;

    public DefaultTemplateLoader(EmuConfig emuConfig) {
        this.emuConfig = emuConfig;
    }

    @Override
    public Template loadTemplate(String templateId) {
        Template template = new Template(templateId);
        template.setTemplateContent(
            newPush().id("messageId").correlationId("correlationId").build());
        /* as copied from original resolveMessage(JsonNode json) method */
//        String actualMessageName = json.at("/message_template").textValue();
//        String filePath = emuConfig.templateMessagesDirectory() + actualMessageName;
//        try {
//            return String.join("\n", Files.readAllLines(Paths.get(filePath)));
//        } catch (IOException e) {
//            throw new RuntimeException("file not found :" + filePath, e);
//        }
        return template;
    }

    @Override
    public List<Template> loadTemplateList() throws LoaderEx {
        List<Template> filesList = new ArrayList<>();
        try {
            File folder = new File(emuConfig.templateMessagesDirectory());
            Files.list(Paths.get(folder.getAbsolutePath()))
                .filter(s -> s.toString().endsWith(".xml"))
                .sorted()
                .forEach(e -> filesList.add(new Template(e.toFile().getName())));
        } catch (IOException e) {
            if (e instanceof java.nio.file.NoSuchFileException) {
                throw new DirectoryNotFoundEx(e);
            }
            throw new LoaderEx(e);
        }
        return filesList;
    }
}
