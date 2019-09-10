package eu.cise.emulator.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.emulator.app.core.InstanceID;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class CiseEmuConfiguration extends Configuration {

    @NotEmpty
    private String instanceName = "#NeedTobeLoaded#";
    @NotEmpty
    private String instanceVersion = "#NeedTobeLoaded#";

    @JsonProperty
    public String getInstanceName() {
        return instanceName;
    }

    @JsonProperty
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }



  @JsonProperty
  public String getInstanceVersion() {
        return instanceName;
  }

  @JsonProperty
  public void setInstanceVersion(String version) {
        this.instanceVersion = version;
  }

    public InstanceID buildInstance() {

        return new InstanceID(instanceName,instanceName);
    }

}
