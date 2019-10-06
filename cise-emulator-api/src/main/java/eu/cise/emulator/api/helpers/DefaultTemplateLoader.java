package eu.cise.emulator.api.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.EmuConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DefaultTemplateLoader {

    private final EmuConfig emuConfig;

    public DefaultTemplateLoader(EmuConfig emuConfig) {
        this.emuConfig = emuConfig;
    }

    public String resolveMessage(JsonNode json) {
        String actualMessageName = json.at("/message_template").textValue();
        String filePath = emuConfig.messageTemplateDir() + actualMessageName;
        try {
            return String.join("\n", Files.readAllLines(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("file not found :" + filePath, e);
        }
    }


    public List<String> listMessages() {
        List<String> filesList = new ArrayList<>();
        try {
            File folder = new File(emuConfig.messageTemplateDir());
            Files.list(Paths.get(folder.getAbsolutePath()))
                    .filter(s -> s.toString().endsWith(".xml"))
                    .sorted()
                    .forEach(e -> filesList.add(e.toFile().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filesList;
    }
}
