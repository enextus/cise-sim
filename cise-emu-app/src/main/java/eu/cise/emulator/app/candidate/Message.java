package eu.cise.emulator.app.candidate;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@XmlRootElement
public class Message implements Serializable {
    private User user;
    private MessageType type;
    private String data;

    @JsonProperty
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    @JsonProperty
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    @JsonProperty
    public String getData(){
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
}