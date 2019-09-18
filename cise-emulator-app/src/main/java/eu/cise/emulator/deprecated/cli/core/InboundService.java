package eu.cise.emulator.deprecated.cli.core;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import eu.cise.emulator.deprecated.cli.core.sub.MessageValidator;
import eu.cise.emulator.deprecated.cli.core.sub.ValidationResult;
import eu.cise.emulator.deprecated.cli.emulator.AcknowledgementHelper;
import eu.cise.emulator.deprecated.cli.util.InteractIOFile;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InboundService {
    private static final org.slf4j.Logger LOGGER;
    private static final AcknowledgementHelper ACKNOWLEDGEMENT_HELPER;
    private static String fileNameTemplate;
    private static Executor preparedConfiguration = null;

    private XmlMapper xmlMapper = new DefaultXmlMapper.Pretty();
    private MessageValidator validator = new MessageValidator();


    public static final String ERROR = "ERROR";
    public static final String ACK = "ACK";
    public static final String MESSAGE_MODAL = "RECEIVE";

    static {
        LOGGER = LoggerFactory.getLogger(InboundService.class);
        ACKNOWLEDGEMENT_HELPER = new AcknowledgementHelper();
    }

    public InboundService() {
    }

    public static void init(Executor configuredDelegate, String filenameTemplate) {
        preparedConfiguration = configuredDelegate;
        fileNameTemplate = filenameTemplate;
    }

    public Acknowledgement receive(Message message) {
        String inputXmlMessage = "";
        try {
            inputXmlMessage = xmlMapper.toXML(message);
        } catch (Exception e) {
            LOGGER.error("fail to convert  message to string", e);
        }
        String createdFile = InteractIOFile.createRef(this.fileNameTemplate, MESSAGE_MODAL, new StringBuffer(inputXmlMessage));
        if (createdFile == null) {
            LOGGER.error("fail to create input", new IOException("unable to create send file : " + InteractIOFile.getFilename(this.fileNameTemplate, null, MESSAGE_MODAL, "")));
        }
        ValidationResult validResult = preparedConfiguration.validateIncoming(inputXmlMessage);
        LOGGER.info("inputXmlMessage validated {} : xml {} and conformed structure:{} signature {} semantic {}", validResult.isOK(preparedConfiguration.getConfig().getSignatureOnReceive().contains("true")), validResult.isOkXML(), validResult.isOkEntity(), validResult.isOkSignedEntity(), validResult.isOkSemantic());
        String ackMessage = "";
        String filePost = ACK;
        if (!validResult.isOkEntity()) {
            ackMessage = preparedConfiguration.acknowledgmentFailMessage(inputXmlMessage, "BAD_REQUEST", "content could not be validated as Entity");
            filePost = ERROR;
        } else if (preparedConfiguration.getConfig().getSignatureOnReceive().equals("true") && !validResult.isOkSignedEntity()) {
            ackMessage = preparedConfiguration.acknowledgmentFailMessage(inputXmlMessage, "SECURITY_ERROR", "signature error");
            filePost = ERROR;
        } else {
            ackMessage = preparedConfiguration.acknowledgmentSuccessMessage(inputXmlMessage);
        }


        //ackMessage = acknowledgementHelper.increaseAckCodeWithSender(ackMessage);
        String createdreffile = InteractIOFile.createRelativeRef(fileNameTemplate, filePost, createdFile, MESSAGE_MODAL, new StringBuffer(ackMessage));
        LOGGER.warn("New content RECEIVED with timestamp {} result in {} / see AcknowledgementFile://{}", createdFile, filePost, InteractIOFile.getFilename(fileNameTemplate, createdFile, MESSAGE_MODAL, filePost));

        if (createdreffile == null) // fail safe ?
            LOGGER.error("fail to create input", new IOException("New content RECEIVED with timestamp " + createdFile + " / unable to create ack file : " + InteractIOFile.getFilename(this.fileNameTemplate, createdreffile, MESSAGE_MODAL, filePost)));
        return xmlMapper.fromXML(ackMessage);

    }
}
