package eu.cise.emulator.app.candidate;

public class MessageRaw {
    private String body;
    private static String NOT_RECEIVED= "NOT_RECEIVED";
    public void MessageRaw(){
        body="Not Received";
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = NOT_RECEIVED;
    }
}
