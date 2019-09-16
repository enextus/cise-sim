package eu.cise.emulator;



public class Configuration {




    public Configuration(String instanceName, String operationMode, String publishedId, String serviceMode, String counterpartUrl, String outputDirectory, String inputDirectory, String sourceMessageDir, String sourcePayloadDir, String signatureOnSend, String signatureOnReceive, String keyStoreFileName, String keyStorePassword, String certificateKey, String certificatePassword, String counterpartCertificateKey, String counterpartCertificateKeyPassword) {
        this.instanceName = instanceName;
        this.operationMode = operationMode;
        this.publishedId = publishedId;
        this.serviceMode = serviceMode;
        this.counterpartUrl = counterpartUrl;
        this.outputDirectory = outputDirectory;
        this.inputDirectory = inputDirectory;
        this.sourceMessageDir = sourceMessageDir;
        this.sourcePayloadDir = sourcePayloadDir;
        this.signatureOnSend = signatureOnSend;
        this.signatureOnReceive = signatureOnReceive;
        this.keyStoreFileName = keyStoreFileName;
        this.keyStorePassword = keyStorePassword;
        this.certificateKey = certificateKey;
        this.certificatePassword = certificatePassword;
        this.counterpartCertificateKey = counterpartCertificateKey;
        this.counterpartCertificateKeyPassword = counterpartCertificateKeyPassword;
    }



    @Override
    public String toString() {
        return "Configuration{" +
                "instanceName='" + instanceName + '\'' +
                ", operationMode='" + operationMode + '\'' +
                ", publishedId='" + publishedId + '\'' +
                ", serviceMode='" + serviceMode + '\'' +
                ", counterpartUrl='" + counterpartUrl + '\'' +
                ", outputDirectory='" + outputDirectory + '\'' +
                ", inputDirectory='" + inputDirectory + '\'' +
                ", sourceMessageDir='" + sourceMessageDir + '\'' +
                ", sourcePayloadDir='" + sourcePayloadDir + '\'' +
                ", signatureOnSend='" + signatureOnSend + '\'' +
                ", signatureOnReceive='" + signatureOnReceive + '\'' +
                ", keyStoreFileName='" + keyStoreFileName + '\'' +
                ", keyStorePassword='" + keyStorePassword + '\'' +
                ", certificateKey='" + certificateKey + '\'' +
                ", certificatePassword='" + certificatePassword + '\'' +
                ", counterpartCertificateKey='" + counterpartCertificateKey + '\'' +
                ", counterpartCertificateKeyPassword='" + counterpartCertificateKeyPassword + '\'' +
                '}';
    }


    private String instanceName = "#NeedTobeLoaded#";

    public String getInstanceName() {
        return instanceName;
    }


    private String operationMode; //"operation.mode"


    private String publishedId; //"operation.publishedId"


    private String serviceMode; //"operation.serviceMode"


    private String counterpartUrl; //"operation.counterpartUrl"


    private String outputDirectory; //"operation.outputDirectory"


    private String inputDirectory; //"operation.inputDirectory"


    private String sourceMessageDir; //"operation.sourceMessageDir"


    private String sourcePayloadDir; //"operation.sourcePayloadDir"


    public String getOperationMode() {
        return operationMode;
    }


    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }


    public String getPublishedId() {
        return publishedId;
    }


    public String getServiceMode() {
        return serviceMode;
    }


    public String getCounterpartUrl() {
        return counterpartUrl;
    }


    public String getOutputDirectory() {
        return outputDirectory;
    }


    public String getInputDirectory() {
        return inputDirectory;
    }


    public String getSourceMessageDir() {
        return sourceMessageDir;
    }


    public String getSourcePayloadDir() {
        return sourcePayloadDir;
    }


    private String signatureOnSend;


    private String signatureOnReceive;


    private String keyStoreFileName;


    private String keyStorePassword;


    private String certificateKey;


    private String certificatePassword;


    private String counterpartCertificateKey;


    private String counterpartCertificateKeyPassword;


    public String getSignatureOnSend() {
        return signatureOnSend;
    }


    public String getSignatureOnReceive() {
        return signatureOnReceive;
    }


    public String getKeyStoreFileName() {
        return keyStoreFileName;
    }


    public String getKeyStorePassword() {
        return keyStorePassword;
    }


    public String getCertificateKey() {
        return certificateKey;
    }


    public String getCertificatePassword() {
        return certificatePassword;
    }


    public String getCounterpartCertificate() {
        return counterpartCertificateKey;
    }


    public String getCounterpartCertificatePassword() {
        return counterpartCertificateKeyPassword;
    }


}
