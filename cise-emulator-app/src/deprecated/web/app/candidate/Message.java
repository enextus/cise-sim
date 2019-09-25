package eu.cise.emulator.deprecated.web.app.candidate;


import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.emulator.deprecated.web.app.core.Member;
import eu.cise.emulator.deprecated.web.app.core.MessageType;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@XmlRootElement
public class Message implements Serializable {
    private Member member;
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
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    @JsonProperty
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
