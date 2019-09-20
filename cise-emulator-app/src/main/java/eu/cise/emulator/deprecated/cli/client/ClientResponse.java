package eu.cise.emulator.deprecated.cli.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientResponse {

    @JsonProperty
    private String responseCode;

    @JsonProperty
    private String acknowledgeContent;

    public ClientResponse(String responseCode, String acknowledgeContent) {
        this.responseCode = responseCode;
        this.acknowledgeContent = acknowledgeContent;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getAcknowledgeContent() {
        return acknowledgeContent;
    }

    public void setAcknowledgeContent(String acknowledgeContent) {
        this.acknowledgeContent = acknowledgeContent;
    }

}
