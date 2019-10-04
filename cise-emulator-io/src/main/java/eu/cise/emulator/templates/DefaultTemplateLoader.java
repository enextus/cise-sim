package eu.cise.emulator.templates;

import static eu.eucise.helpers.PushBuilder.newPush;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.exceptions.DirectoryNotFoundEx;
import eu.cise.emulator.exceptions.LoaderEx;
import eu.cise.servicemodel.v1.message.Push;
import eu.eucise.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultTemplateLoader implements TemplateLoader {

    private final EmuConfig emuConfig;
    private final XmlMapper xmlMapper;

    public DefaultTemplateLoader(XmlMapper xmlMapper, EmuConfig emuConfig) {
        this.emuConfig = emuConfig;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public Template loadTemplate(String templateId) {
        Push fakeMessage = newPush().id("messageId").correlationId("correlationId").build();
        Template template = new Template(templateId, "templateName", xmlMapper.toXML(fakeMessage));
        /* as copied from original resolveMessage(JsonNode json) method */
//        String actualMessageName = json.at("/message_template").textValue();
//        String filePath = emuConfig.templateMessagesDirectory() + actualMessageName;
//        try {
//            return String.join("\n", Files.readAllLines(Paths.get(filePath)));
//        } catch (IOException e) {
//            throw new RuntimeException("file not found :" + filePath, e);
//        }
        return template;

//        XmlMapper nonValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
//        return new Template(templateId, "templateName", nonValidatingXmlMapper.toXML(fakeMessage));
    }

    @Override
    public List<Template> loadTemplateList() throws LoaderEx {
        try {
            return Files.list(messageTemplatePath(emuConfig.messageTemplateDir()))
                .filter(s -> s.toString().endsWith(".xml"))
                .sorted()
                .map(e -> e.toFile().getName())
                .map(e -> new Template(e, ""))
                .collect(Collectors.toList());

        } catch (NoSuchFileException e) {
            throw new DirectoryNotFoundEx(e);
        } catch (IOException e) {
            throw new LoaderEx(e);
        }
    }

    private Path messageTemplatePath(String pathname) {
        return Paths.get(new File(pathname).getAbsolutePath());
    }
}
