package eu.cise.emulator.api;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Response;
import java.io.Serializable;

public class MessageApiDto implements Serializable {

    @JsonProperty("status")
    int status;
    @JsonProperty("errorDetail")
    String errorDetail;
    @JsonProperty("body")
    String body;
    @JsonProperty("acknowledge")
    String acknowledge;
    @JsonProperty("error")
    Boolean error;

    public MessageApiDto() {

    }

    public MessageApiDto(Response.StatusType status, String errorDetail, String contentAcknowledge, String contentMessageString) {
        this.status = status.getStatusCode();
        this.errorDetail = errorDetail;
        this.body = contentMessageString;
        this.acknowledge = contentAcknowledge;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
