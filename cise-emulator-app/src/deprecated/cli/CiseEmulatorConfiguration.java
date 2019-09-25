package eu.cise.emulator.deprecated.cli;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class CiseEmulatorConfiguration extends Configuration {

    /*----------operation-----------*/
    //operation x 8
    @NotEmpty
    private String operationMode; //"operation.mode"

    @NotEmpty
    private String publishedId; //"operation.publishedId"

    @NotEmpty
    private String serviceMode; //"operation.serviceMode"

    @NotEmpty
    private String counterpartUrl; //"operation.counterpartUrl"

    @NotEmpty
    private String outputDirectory; //"operation.outputDirectory"

    @NotEmpty
    private String inputDirectory; //"operation.inputDirectory"

    @NotEmpty
    private String sourceMessageDir; //"operation.sourceMessageDir"

    @NotEmpty
    private String sourcePayloadDir; //"operation.sourcePayloadDir"

    @JsonProperty("operation.mode")
    public String getOperationMode() {
        return operationMode;
    }

    @JsonProperty("operation.mode")
    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    @JsonProperty("operation.publishedId")
    public String getPublishedId() {
        return publishedId;
    }

    @JsonProperty("operation.publishedId")
    public void setPublishedId(String publishedId) {
        this.publishedId = publishedId;
    }

    @JsonProperty("operation.serviceMode")
    public String getServiceMode() {
        return serviceMode;
    }

    @JsonProperty("operation.serviceMode")
    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode;
    }

    @JsonProperty("operation.counterpartUrl")
    public String getCounterpartUrl() {
        return counterpartUrl;
    }

    @JsonProperty("operation.counterpartUrl")
    public void setCounterpartUrl(String counterpartUrl) {
        this.counterpartUrl = counterpartUrl;
    }

    @JsonProperty("operation.outputDirectory")
    public String getOutputDirectory() {
        return outputDirectory;
    }

    @JsonProperty("operation.outputDirectory")
    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @JsonProperty("operation.inputDirectory")
    public String getInputDirectory() {
        return inputDirectory;
    }

    @JsonProperty("operation.inputDirectory")
    public void setInputDirectory(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    @JsonProperty("operation.sourceMessageDir")
    public String getSourceMessageDir() {
        return sourceMessageDir;
    }

    @JsonProperty("operation.sourceMessageDir")
    public void setSourceMessageDir(String sourceMessageDir) {
        this.sourceMessageDir = sourceMessageDir;
    }

    @JsonProperty("operation.sourcePayloadDir")
    public String getSourcePayloadDir() {
        return sourcePayloadDir;
    }

    @JsonProperty("operation.sourcePayloadDir")
    public void setSourcePayloadDir(String sourcePayloadDir) {
        this.sourcePayloadDir = sourcePayloadDir;
    }
    /*----------signature-----------*/

    @NotEmpty
    private String signatureOnSend;

    @NotEmpty
    private String signatureOnReceive;

    @NotEmpty
    private String keyStoreFileName;

    @NotEmpty
    private String keyStorePassword;

    @NotEmpty
    private String certificateKey;

    @NotEmpty
    private String certificatePassword;

    @NotEmpty
    private String counterpartCertificateKey;


    private String counterpartCertificateKeyPassword;


    @JsonProperty("signature.OnSend")
    public String getSignatureOnSend() {
        return signatureOnSend;
    }

    @JsonProperty("signature.OnSend")
    public void setSignatureOnSend(String signatureOnSend) {
        this.signatureOnSend = signatureOnSend;
    }

    @JsonProperty("signature.OnReceive")
    public String getSignatureOnReceive() {
        return signatureOnReceive;
    }

    @JsonProperty("signature.OnReceive")
    public void setSignatureOnReceive(String signatureOnReceive) {
        this.signatureOnReceive = signatureOnReceive;
    }

    @JsonProperty("signature.keyStoreFileName")
    public String getKeyStoreFileName() {
        return keyStoreFileName;
    }

    @JsonProperty("signature.keyStoreFileName")
    public void setKeyStoreFileName(String keyStoreFileName) {
        this.keyStoreFileName = keyStoreFileName;
    }

    @JsonProperty("signature.keyStorePassword")
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    @JsonProperty("signature.keyStorePassword")
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    @JsonProperty("signature.certificateKey")
    public String getCertificateKey() {
        return certificateKey;
    }

    @JsonProperty("signature.certificateKey")
    public void setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
    }

    @JsonProperty("signature.certificatePassword")
    public String getCertificatePassword() {
        return certificatePassword;
    }

    @JsonProperty("signature.certificatePassword")
    public void setCertificatePassword(String certificatePassword) {
        this.certificatePassword = certificatePassword;
    }

    @JsonProperty("signature.counterpartCertificateKey")
    public String getCounterpartCertificate() {
        return counterpartCertificateKey;
    }

    @JsonProperty("signature.counterpartCertificateKey")
    public void setCounterpartCertificate(String counterpartCertificateKey) {
        this.counterpartCertificateKey = counterpartCertificateKey;
    }

    @JsonProperty("signature.counterpartCertificateKeyPassword")
    public String getCounterpartCertificatePassword() {
        return counterpartCertificateKeyPassword;
    }

    @JsonProperty("signature.counterpartCertificateKeyPassword")
    public void setCounterpartCertificatePassword(String counterpartCertificateKeyPassword) {
        this.counterpartCertificateKeyPassword = counterpartCertificateKeyPassword;
    }
}
