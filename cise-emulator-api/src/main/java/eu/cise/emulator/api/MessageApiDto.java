package eu.cise.emulator.api;


import javax.ws.rs.core.Response;
import java.io.Serializable;

public class MessageApiDto implements Serializable {

    final Response.StatusType status;
    final String body;
    final String acknowledge;

    public Response.StatusType getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public String getAcknowledge() {
        return acknowledge;
    }


    public MessageApiDto(Response.StatusType status, String contentAcknowledge, String contentMessageString) {
        this.status = status;
        this.body = contentMessageString;
        this.acknowledge = contentAcknowledge;
    }


    public boolean isError() {
        return (status.getStatusCode() >= 400);
    }


}
