package eu.cise.sim.api.representation;

import eu.cise.sim.engine.SendParam;

import java.io.Serializable;

public class SendingDataWrapper implements Serializable {

    private static final long serialVersionUID = -3948483585312107124L;

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
