package eu.europa.ec.jrc.marex;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.text.SimpleDateFormat;

public class CiseEmulatorConfiguration extends Configuration {

    /*----------operation-----------*/
    //operation x 8
    @NotEmpty
    private String OperationMode;//"operation.mode"

    @NotEmpty
    private String PublishedId;//"operation.publishedId"

    @NotEmpty
    private String ServiceMode;//"operation.serviceMode"

    @NotEmpty
    private String CounterpartUrl;//"operation.counterpartUrl"

    @NotEmpty
    private String OutputDirectory;//"operation.outputDirectory"

    @NotEmpty
    private String InputDirectory;//"operation.inputDirectory"

    @NotEmpty
    private String SourceMessageDir;//"operation.sourceMessageDir"

    @NotEmpty
    private String SourcePayloadDir;//"operation.sourcePayloadDir"

    @JsonProperty("operation.mode")
    public String getOperationMode() {
        return OperationMode;
    }

    @JsonProperty("operation.mode")
    public void setOperationMode(String OperationMode) {
        this.OperationMode = OperationMode;
    }

    @JsonProperty("operation.publishedId")
    public String getPublishedId() {
        return PublishedId;
    }

    @JsonProperty("operation.publishedId")
    public void setPublishedId(String PublishedId) {
        this.PublishedId = PublishedId;
    }

    @JsonProperty("operation.serviceMode")
    public String getServiceMode() {
        return ServiceMode;
    }

    @JsonProperty("operation.serviceMode")
    public void setServiceMode(String ServiceMode) {
        this.ServiceMode = ServiceMode;
    }

    @JsonProperty("operation.counterpartUrl")
    public String getCounterpartUrl() {
        return CounterpartUrl;
    }

    @JsonProperty("operation.counterpartUrl")
    public void setCounterpartUrl(String CounterpartUrl) {
        this.CounterpartUrl = CounterpartUrl;
    }

    @JsonProperty("operation.outputDirectory")
    public String getOutputDirectory() {
        return OutputDirectory;
    }

    @JsonProperty("operation.outputDirectory")
    public void setOutputDirectory(String OutputDirectory) {
        this.OutputDirectory = OutputDirectory;
    }

    @JsonProperty("operation.inputDirectory")
    public String getInputDirectory() {
        return InputDirectory;
    }

    @JsonProperty("operation.inputDirectory")
    public void setInputDirectory(String InputDirectory) {
        this.InputDirectory = InputDirectory;
    }

    @JsonProperty("operation.sourceMessageDir")
    public String getSourceMessageDir() {
        return SourceMessageDir;
    }

    @JsonProperty("operation.sourceMessageDir")
    public void setSourceMessageDir(String SourceMessageDir) {
        this.SourceMessageDir = SourceMessageDir;
    }

    @JsonProperty("operation.sourcePayloadDir")
    public String getSourcePayloadDir() {
        return SourcePayloadDir;
    }

    @JsonProperty("operation.sourcePayloadDir")
    public void setSourcePayloadDir(String SourcePayloadDir) {
        this.SourcePayloadDir = SourcePayloadDir;
    }
    /*----------signature-----------*/

    @NotEmpty
    private String SignatureOnSend;

    @NotEmpty
    private String SignatureOnReceive;

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
        return SignatureOnSend;
    }

    @JsonProperty("signature.OnSend")
    public void setSignatureOnSend(String SignatureOnSend) {
        this.SignatureOnSend = SignatureOnSend;
    }

    @JsonProperty("signature.OnReceive")
    public String getSignatureOnReceive() {
        return SignatureOnReceive;
    }

    @JsonProperty("signature.OnReceive")
    public void setSignatureOnReceive(String SignatureOnReceive) {
        this.SignatureOnReceive = SignatureOnReceive;
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
    public String getCounterpartCertificatePassword() {return counterpartCertificateKeyPassword; }

    @JsonProperty("signature.counterpartCertificateKeyPassword")
    public void setCounterpartCertificatePassword(String counterpartCertificateKeyPassword) {
        this.counterpartCertificateKeyPassword = counterpartCertificateKeyPassword;
    }
}
