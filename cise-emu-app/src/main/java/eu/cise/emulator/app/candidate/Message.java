package eu.cise.emulator.app.candidate;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@XmlRootElement
public class Message implements Serializable {

    private int id;
    private String body;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String name) {
        this.body = name;
    }

}