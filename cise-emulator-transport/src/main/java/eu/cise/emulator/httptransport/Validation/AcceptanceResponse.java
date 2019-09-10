package eu.cise.emulator.httptransport.Validation;

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

/* REPENTIR : from  previous conformance implementation b9d2690e hornste
package jrc.cise.gw.preprocessing;
 */