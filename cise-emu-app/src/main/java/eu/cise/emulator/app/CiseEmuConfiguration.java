package eu.cise.emulator.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.emulator.app.core.InstanceID;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class CiseEmuConfiguration extends Configuration {

    @NotEmpty
    private String instanceName = "#NeedTobeLoaded#";
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


}
