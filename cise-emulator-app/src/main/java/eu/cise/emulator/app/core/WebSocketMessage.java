package eu.cise.emulator.app.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSocketMessage implements Serializable {

    private String member;
    private WebSocketMessageType type;
    private String data;
    private String acknowledgment;
    private String status;

    public WebSocketMessageType getType() {
        return type;
    }

    public void setType(WebSocketMessageType type) {
        this.type = type;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
    @JsonValue
    public void setAcknowledgment(String acknowledgment) {
        this.acknowledgment = acknowledgment;
    }

    public String getAcknowledgment() {
        return acknowledgment;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
