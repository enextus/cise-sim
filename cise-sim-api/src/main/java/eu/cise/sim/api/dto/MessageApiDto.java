package eu.cise.sim.api.dto;


import java.io.Serializable;
import java.util.Objects;

public class MessageApiDto implements Serializable {

    private static final long serialVersionUID = 3399816870017528741L;

    private String body;
    private String acknowledge;

    public MessageApiDto() {
    }

    public MessageApiDto(String acknowledge, String body) {
        this.body = body;
        this.acknowledge = acknowledge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageApiDto that = (MessageApiDto) o;
        return body.equals(that.body) &&
                acknowledge.equals(that.acknowledge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, acknowledge);
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


