package eu.europa.ec.jrc.marex.core.sub;

public enum AcceptanceResponse {
    PROCESSED, XML_MALFORMED, VALIDATION_ERROR, INVALID_SIGNATURE, INTERNAL_ERROR;

    private String xmlBody = "";

    public AcceptanceResponse withXmlBody(String xmlBody) {
        this.xmlBody = xmlBody;
        return this;
    }

    public String getXmlBody() {
        return xmlBody;
    }
}
