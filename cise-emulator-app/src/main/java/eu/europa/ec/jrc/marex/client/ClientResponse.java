package eu.europa.ec.jrc.marex.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientResponse {

    @JsonProperty
    private String ResponseCode;

    @JsonProperty
    private String AcknoledgeContent;

    public ClientResponse(String responseCode, String acknoledgeContent) {
        ResponseCode = responseCode;
        AcknoledgeContent = acknoledgeContent;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getAcknoledgeContent() {
        return AcknoledgeContent;
    }

    public void setAcknoledgeContent(String acknoledgeContent) {
        AcknoledgeContent = acknoledgeContent;
    }

}
