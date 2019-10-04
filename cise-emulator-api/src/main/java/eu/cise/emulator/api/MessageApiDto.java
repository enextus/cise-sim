package eu.cise.emulator.api;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MessageApiDto implements Serializable {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("errorDetail")
    private String errorDetail;

    @JsonProperty("body")
    private String body;

    @JsonProperty("acknowledge")
    private String acknowledge;

    @JsonProperty("error")
    private Boolean error;

    public MessageApiDto() {

    }

    public MessageApiDto(Integer status, String errorDetail, String acknowledge, String body) {
        this.status = status;
        this.errorDetail = errorDetail;
        this.body = body;
        this.acknowledge = acknowledge;
    }


    public Boolean getError() {
        return status >= 400;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(String acknowledge) {
        this.acknowledge = acknowledge;
    }
}


