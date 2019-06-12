package eu.cise.emulator.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.emulator.app.core.InstanceID;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class CiseEmuConfiguration extends Configuration {

    @NotEmpty
    private String instanceName = "Emulator";

    @JsonProperty
    public String getInstanceName() {
        return instanceName;
    }

    @JsonProperty
    public void setInstanceName(String defaultName) {
        this.instanceName = defaultName;
    }

    public InstanceID buildInstance() {
        return new InstanceID(instanceName);
    }


//    private final SimLogger logger;
//
//    private final SimConfig config;
//
//    private final SourceStreamProcessor streamProcessor;
//
//    private final Sender sender;
//
//    private final XmlMapper xmlMapper;
//
//    private final MessageValidator validator;
//
//    this.logger = logger;
//    this.config = config;
//    this.xmlMapper = xmlMapper;
//    this.validator = validator;

}
