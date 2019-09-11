package eu.cise.emulator.websocket.repentir;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.cise.emulator.websocket.user.User;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSocketMessage implements Serializable {

    private User user;
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

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public String getData() {
        return data;

    }

    public void setData(String data) {

        this.data = data;
    }

    public String getAcknowledgment() {
        return acknowledgment;
    }

    public void setAcknowledgment(String acknowledgment) {
        this.acknowledgment = acknowledgment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
