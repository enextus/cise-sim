package eu.europa.ec.jrc.marex.core.sub;

import java.io.Serializable;

public class ValidationResult implements Serializable {

    private boolean okXML = false;
    private boolean okEntity = false;
    private boolean okSignedEntity = false;
    private boolean okSemantic = false;

    public ValidationResult() {
    }

    public boolean isOkXML() {
        return okXML;
    }

    public void setOkXML(boolean okXML) {
        this.okXML = okXML;
    }

    public boolean isOkEntity() {
        return okEntity;
    }

    public void setOkEntity(boolean okEntity) {
        this.okEntity = okEntity;
    }

    public boolean isOkSignedEntity() {
        return okSignedEntity;
    }

    public void setOkSignedEntity(boolean okSignedEntity) {
        this.okSignedEntity = okSignedEntity;
    }

    public boolean isOkSemantic() {
        return okSemantic;
    }

    public void setOkSemantic(boolean okSemantic) {
        this.okSemantic = okSemantic;
    }


    public boolean isOK(boolean requireSigned) {
        return (this.okXML && this.okEntity && this.okSemantic && ((!requireSigned) || okSignedEntity));
    }


}
