package eu.cise.emulator.api.resources;

import eu.cise.emulator.SendParam;

import java.io.Serializable;

public class SendingDataWrapper implements Serializable {

    private SendParam param;
    private String templateHash;

    public SendingDataWrapper() {
    }
    public SendingDataWrapper(SendParam param, String templateHash) {
        this.param = param;
        this.templateHash = templateHash;
    }

    public SendParam getParam() {
        return param;
    }

    public void setParam(SendParam param) {
        this.param = param;
    }

    public String getTemplateHash() {
        return templateHash;
    }

    public void setTemplateHash(String templateHash) {
        this.templateHash = templateHash;
    }

}
