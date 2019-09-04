package eu.europa.ec.jrc.marex.core;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration;
import eu.europa.ec.jrc.marex.core.sub.ValidationResult;
import eu.europa.ec.jrc.marex.emulator.AcknowledgementHelper;
import eu.europa.ec.jrc.marex.util.InteractIOFile;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InboundService {
    private Executor ExcecutionDelegate = null;
    private static final AcknowledgementHelper acknowledgementHelper;
    private String fileNameTemplate;
    private XmlMapper xmlMapper = new DefaultXmlMapper();
    private CiseEmulatorConfiguration emulatorConfig;
    private static final org.slf4j.Logger LOGGER;

    public static final String ERROR = "ERROR";
    public static final String ACK = "ACK";
    public static final String MESSAGE_MODAL = "RECEIVE";

    static {
        LOGGER = LoggerFactory.getLogger(InboundService.class);
        acknowledgementHelper=new AcknowledgementHelper();
    }

    public InboundService() {
    }

    public Acknowledgement Receive(Message message) {

        String inputXmlMessage=xmlMapper.toXML(message);
        String createdFile = InteractIOFile.createRef(this.fileNameTemplate,MESSAGE_MODAL, new StringBuffer(inputXmlMessage));
        if (createdFile == null) {
            LOGGER.error("fail to create input", new IOException("unable to create send file : " + InteractIOFile.getFilename(this.fileNameTemplate, null, MESSAGE_MODAL, "")));
        }
        ValidationResult validResult = ExcecutionDelegate.validateIncoming(inputXmlMessage);
        LOGGER.info("inputXmlMessage validated {} : xml {} and conformed structure:{} signature {} semantic {}", validResult.isOK(emulatorConfig.getSignatureOnReceive().contains("true")), validResult.isOkXML(), validResult.isOkEntity(), validResult.isOkSignedEntity(), validResult.isOkSemantic());
        String ackMessage = "";
        String filePost = ACK;
        if (validResult.isOkEntity()==false) {
            ackMessage = ExcecutionDelegate.AcknowledgmentFailMessage(inputXmlMessage, "BAD_REQUEST", "content could not be validated as Entity");
            filePost=ERROR;
            // LOGGER.warn("BAD_REQUEST ACK sent for  structural {}, semantic {} reasons ; see file://{}", validResult.isOkEntity(), validResult.isOkSemantic(),InteractIOFile.getFilename(this.fileNameTemplate, filePost,MESSAGE_MODAL, filePost));
        }else if (emulatorConfig.getSignatureOnReceive().equals("true") && validResult.isOkSignedEntity()==false){
            ackMessage =  ExcecutionDelegate.AcknowledgmentFailMessage(inputXmlMessage,"SECURITY_ERROR","signature error");
            filePost= ERROR;
            // LOGGER.warn("SECURITY ERROR ACK sent for signature required on start {} signature validated {}  ; see file://{} ", validResult.isOK(emulatorConfig.getSignatureOnReceive().contains("true")),validResult.isOkSignedEntity(),InteractIOFile.getFilename(this.fileNameTemplate, filePost,MESSAGE_MODAL, filePost));

        }
        else{
            ExcecutionDelegate.AcknowledgmentSuccessMessage(inputXmlMessage);}


        AcknowledgementHelper acknowledgementHelper=new AcknowledgementHelper();
        Acknowledgement realAckmessage = new Acknowledgement(); //  acknowledgementHelper.increaseAckCodeWithSender(ackMessage);
        String createdreffile = InteractIOFile.createRelativeRef(fileNameTemplate, filePost, createdFile,MESSAGE_MODAL, new StringBuffer(ackMessage));
        LOGGER.warn("New content RECEIVED with timestamp {} result in {} / see AcknowledgementFile://{}", createdFile, filePost, InteractIOFile.getFilename(fileNameTemplate, createdFile, MESSAGE_MODAL,filePost));

        if (createdreffile != null){
            return (realAckmessage);
        }        else{
            LOGGER.error("fail to create input", new IOException("New content RECEIVED with timestamp "+createdFile+" / unable to create ack file : "
                    + InteractIOFile.getFilename(this.fileNameTemplate, createdreffile,MESSAGE_MODAL, filePost)));}
        return realAckmessage;

    }
}