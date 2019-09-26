package eu.cise.emulator.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.ws.rs.core.Response;
import java.io.Serializable;

public class MessageApiDto implements Serializable {


    @JsonProperty("status")
    final int status;

    @JsonProperty("body")
    final String body;

    @JsonProperty("acknowledge")
    final String acknowledge;


    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public String getAcknowledge() {
        return acknowledge;
    }


    public MessageApiDto(Response.StatusType status, String contentAcknowledge, String contentMessageString) {
        this.status = status.getStatusCode();
        this.body = contentMessageString;
        this.acknowledge = contentAcknowledge;
    }


    public boolean isError() {
        return (status >= 400);
    }


}
