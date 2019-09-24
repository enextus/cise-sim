package eu.cise.emulator.api;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;

public class CiseMessageResponse {
    String acknowledgementContent;
    String messageContent;
    boolean unmarshallableMessage = false;


    public CiseMessageResponse(XmlMapper mapper, Acknowledgement acknowledgment, Message message) {
        this.messageContent = mapper.toXML(message);
        this.acknowledgementContent = mapper.toXML(acknowledgment);
        unmarshallableMessage = true;
    }

    public CiseMessageResponse(String content) {
        this.messageContent = content;
        this.acknowledgementContent = "";
        /*TODO: produce acknowledge with error */
    }

    public String getAcknowledgementContent() {
        return acknowledgementContent;
    }


    public String getMessageContent() {
        return messageContent;
    }


    public boolean isUnmarshallableMessage() {
        return unmarshallableMessage;
    }

}
