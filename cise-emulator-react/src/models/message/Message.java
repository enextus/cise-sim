package go.ninja.javaplayground.messagernode.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import go.ninja.javaplayground.messagernode.model.member.User;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {

    private User user;
    private MessageType type;
    private String data;

    @JsonProperty
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @JsonProperty
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @JsonProperty
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
